package by.belyahovich.cafiteria_back.repository.user;

import by.belyahovich.cafiteria_back.domain.Order;
import by.belyahovich.cafiteria_back.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("User repository Integration Tests")
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addUsers.sql"})
    @Test
    public void findAll_WithThreeCount_shouldPropperlyFindAllUsers() {
        //when
        int COUNT_OF_USERS_ON_DB = 3;
        Iterable<User> fondUsers = userRepository.findAll();

        //then
        assertThat(fondUsers).hasSize(COUNT_OF_USERS_ON_DB);
    }

    @Sql(scripts = {"/sql/clearDatabases.sql"})
    @Test
    public void save_WithNewUser_shouldPropperlySaveNewUser() {
        //given
        User user = new User();
        user.setName("Vilat");
        user.setSurname("Volochai");
        user.setLocation("Kiev");
        user.setPhone(375334423863L);
        user.setEmail("vilat@gmail.com");
        user.setPassword("qwer4312");
        user.setListOrders(Collections.emptyList());
        userRepository.save(user);

        //when
        Optional<User> maybeUser = userRepository.findById(user.getId());

        //then
        assertAll(
                () -> assertThat(maybeUser).isPresent(),
                () -> maybeUser.ifPresent(userCheck -> assertThat(userCheck).isEqualTo(user))
        );
    }

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addUsers.sql"})
    @Test
    public void delete_WithExistingUser_shouldPropperlyDeleteUser() {
        //given
        long SOME_RANDOM_USER_NUMBER = 1;
        int COUNT_OF_USERS_ON_DB_AFTER_DELETE = 2;
        Optional<User> userForDelete = userRepository.findById(SOME_RANDOM_USER_NUMBER);
        assertThat(userForDelete).isPresent();
        userRepository.delete(userForDelete.get());

        //when
        Iterable<User> userFindList = userRepository.findAll();
        Optional<User> findDeletedUser = userRepository.findById(SOME_RANDOM_USER_NUMBER);

        //then
        assertAll(
                () -> assertThat(userFindList).hasSize(COUNT_OF_USERS_ON_DB_AFTER_DELETE),
                () -> assertThat(findDeletedUser).isNotPresent()
        );
    }

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addUsers.sql"})
    @Test
    public void update_WithNewUserInformation_shouldProperlyUpdateUser() {
        //given
        long SOME_RANDOM_USER_NUMBER = 2;
        Optional<User> userForUpdate = userRepository.findById(SOME_RANDOM_USER_NUMBER);
        assertThat(userForUpdate).isPresent();

        //when
        userForUpdate.get().setName("TestName");
        userForUpdate.get().setName("TestSurname");
        userRepository.save(userForUpdate.get());
        Optional<User> userAfterUpdateFromBD = userRepository.findById(SOME_RANDOM_USER_NUMBER);

        //then
        assertAll(
                () -> assertThat(userAfterUpdateFromBD).isPresent(),
                () -> assertThat(userAfterUpdateFromBD).isEqualTo(userForUpdate)
        );
    }

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addOrdersForUser.sql"})
    @Test
    public void getAllOrdersByUserId_WithExistingUser_shouldPropperlyGetAllOrderByUserId() {
        //given
        int COUNT_OF_ORDER_BY_FIRST_USER = 2;
        Iterable<User> allUser = userRepository.findAll();
        long USER_ID_FOR_SEARCH = allUser.iterator().next().getId();

        //when
        List<Order> findedOrderByuserId = userRepositoryJpa.getAllOrdersByUserId(USER_ID_FOR_SEARCH);

        //then
        assertAll(
                () -> assertThat(findedOrderByuserId).isNotNull().isNotEmpty(),
                () -> assertThat(findedOrderByuserId).hasSize(COUNT_OF_ORDER_BY_FIRST_USER)
        );
    }

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addOrdersForUser.sql"})
    @Test
    public void findUserByPhone_WithExistingPhone_souldPropperlyFindUserByPhone(){
        //given
        long USER_ID_FOR_SEARCH = 1;
        Optional<User> expectedUser = userRepository.findById(USER_ID_FOR_SEARCH);
        assertThat(expectedUser).isPresent();

        //when
        User actualUser = userRepositoryJpa.findUserByPhone(expectedUser.get().getPhone());

        //then
        assertThat(actualUser).isEqualTo(expectedUser.get());
    }
}