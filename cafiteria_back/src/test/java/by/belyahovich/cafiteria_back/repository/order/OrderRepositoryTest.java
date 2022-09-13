package by.belyahovich.cafiteria_back.repository.order;

import by.belyahovich.cafiteria_back.domain.Order;
import by.belyahovich.cafiteria_back.domain.User;
import by.belyahovich.cafiteria_back.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Order repository Integration Tests")
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addOrdersForUser.sql"})
    @Test
    public void findAll_WithThreeOrder_shouldPropperlyFindAllOrders() {
        //given
        int COUT_OF_ALL_ORDERS = 3;

        //when
        Iterable<Order> foundUsers = orderRepository.findAll();

        //then
        assertAll(
                () -> assertThat(foundUsers).isNotNull(),
                () -> assertThat(foundUsers).hasSize(COUT_OF_ALL_ORDERS)
        );
    }

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addUsers.sql"})
    @Test
    public void save_WithNewOrder_shouldPropperlyAddNewOrderToExsistingUser() {
        //given
        long USER_NUMBER = 1;
        Order order = new Order();
        Date date = new Date();
        order.setTime(date);
        order.setLocation("Vitebsk");
        order.setComment("Best price in all city");
        Optional<User> actualUser = userRepository.findById(USER_NUMBER);
        assertThat(actualUser).isPresent();

        order.setUser(actualUser.get());

        //when
        orderRepository.save(order);
        Optional<Order> actualOrder = orderRepository.findById(order.getId());

        //then
        assertAll(
                () -> assertThat(actualOrder).isPresent(),
                () -> actualOrder.ifPresent(orderCheck -> assertThat(orderCheck).isEqualTo(order))
        );
    }

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addOrdersForUser.sql"})
    @Test
    public void delete_WitheExistingOrder_shouldPropperlyDeleteOrderFromUser() {
        //given
        long SOME_NUMBER_OF_ORDER_TO_DELETE = 2;
        int EXPECTED_COUNT_OF_ORDER_AFTER_DELETE = 2;
        Optional<Order> orderForDelete = orderRepository.findById(SOME_NUMBER_OF_ORDER_TO_DELETE);
        assertThat(orderForDelete).isPresent();
        orderRepository.delete(orderForDelete.get());

        //when
        Iterable<Order> actualCountOfOrder = orderRepository.findAll();
        Optional<Order> actualDeletedOrder = orderRepository.findById(SOME_NUMBER_OF_ORDER_TO_DELETE);

        //then
        assertAll(
                () -> assertThat(actualCountOfOrder).hasSize(EXPECTED_COUNT_OF_ORDER_AFTER_DELETE),
                () -> assertThat(actualDeletedOrder).isNotPresent()
        );
    }

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addOrdersForUser.sql"})
    @Test
    public void update_WithEditingInformation_shouldPropperlyUpdateOrder() {
        //given
        long SOME_ORDER_NUMBER_FOR_UPDATE = 1;
        Optional<Order> orderForUpdate = orderRepository.findById(SOME_ORDER_NUMBER_FOR_UPDATE);
        assertThat(orderForUpdate).isPresent();

        //when
        orderForUpdate.get().setComment("TestComment");
        orderForUpdate.get().setLocation("TestLocation");
        orderRepository.save(orderForUpdate.get());
        Optional<Order> actualOrder = orderRepository.findById(SOME_ORDER_NUMBER_FOR_UPDATE);

        //then
        assertThat(orderForUpdate).isEqualTo(actualOrder);
    }
}