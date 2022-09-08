package by.belyahovich.cafiteria_back.service.order.impl;

import by.belyahovich.cafiteria_back.config.ResourceNotFoundException;
import by.belyahovich.cafiteria_back.domain.Order;
import by.belyahovich.cafiteria_back.domain.User;
import by.belyahovich.cafiteria_back.repository.order.OrderRepository;
import by.belyahovich.cafiteria_back.service.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Unit-level testing for OrderService")
class OrderServiceImplTest {

    private static final long EXIST_ORDER_ID = 1L;

    private OrderRepository orderRepository;
    private OrderService orderService;
    private Order order;

    @BeforeEach
    public void init(){
        orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderServiceImpl(orderRepository);

        order = new Order();
        order.setId(EXIST_ORDER_ID);
        order.setTime(new Date());
        order.setLocation("Paris");
        order.setUser(new User());
        order.setComment("Please faster then 2PM");
    }

    @Test
    public void findOrderById_IfOrderExisting_shouldPropperlyReturnOrder(){
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        Optional<Order> actualOreder = orderService.findOrderById(order.getId());

        assertAll(
                () -> assertThat(actualOreder).isPresent(),
                () -> assertThat(actualOreder).isEqualTo(Optional.of(order))
        );
    }

    @Test
    public void findOrderById_IfOrderNotExisting_shouldReturnResourceNotFoundException(){
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.findOrderById(anyLong()));
    }

    @Test
    public void allOrders_WithThreeEntity_shouldPropperlyReturnAllOrders(){
        int ORDERS_COUNT = 3;
        List<Order> orderList = Arrays.asList(new Order(), new Order(), new Order());

        when(orderRepository.findAll()).thenReturn(orderList);
        Iterable<Order> actualOrders = orderService.allOrders();

        assertThat(actualOrders).hasSize(ORDERS_COUNT);
    }

    @Test
    public void createOrder_IfOrderNotExiststing_shouldPropperlyCreateNewOrder(){
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(orderRepository.save(order)).thenReturn(order);
        Order actualOrder = orderService.createOrder(order);

        assertThat(actualOrder).isEqualTo(order);
        verify(orderRepository).save(order);
    }

    @Test
    public void createOrder_IfOrderExisting_shouldReturnResourceNotFoundException(){
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.createOrder(order));
    }

    @Test
    public void deleteOrder_IfOrderExisting_shouldPropperlyDeleteOrder(){
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        boolean checkDeleting = orderService.deleteOrder(order);

        assertAll(
                () -> assertThat(checkDeleting).isTrue(),
                () -> verify(orderRepository, times(1)).delete(order)
        );
    }

    @Test
    public void deleteOrder_ifOrderNotExisting_shouldPropperlyNotDeletingOrder(){
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        boolean checkDeleting = orderService.deleteOrder(order);

        assertAll(
                () -> assertThat(checkDeleting).isFalse(),
                () -> verify(orderRepository, times(0)).delete(order)
        );
    }
}