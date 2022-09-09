package by.belyahovich.cafiteria_back.service.order;

import by.belyahovich.cafiteria_back.domain.Order;

import java.util.Optional;

public interface OrderService {
    Optional<Order> findOrderById(long id);
    Iterable<Order> allOrders();
    Order createOrder(Order order);
    boolean deleteOrder(Order order);
}
