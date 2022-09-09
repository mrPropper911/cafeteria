package by.belyahovich.cafiteria_back.service.order.impl;

import by.belyahovich.cafiteria_back.config.ResourceNotFoundException;
import by.belyahovich.cafiteria_back.domain.Order;
import by.belyahovich.cafiteria_back.repository.order.OrderRepository;
import by.belyahovich.cafiteria_back.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> findOrderById(long id) {
        Optional<Order> orderFromBD = orderRepository.findById(id);
        if(orderFromBD.isEmpty()){
            throw new ResourceNotFoundException("Oder with id " + id + " not found");
        }
        return orderFromBD;
    }

    @Override
    public List<Order> allOrders() {
        List<Order> orderList = new ArrayList<>();
        orderRepository.findAll().forEach(orderList::add);
        return orderList;
    }

    @Override
    public Order createOrder(Order order) {
        Optional<Order> searchingOrder = orderRepository.findById(order.getId());
        if (searchingOrder.isPresent()){
            throw new ResourceNotFoundException("Order with id " + order.getId() + " exist");
        }
        return orderRepository.save(order);
    }

    @Override
    public boolean deleteOrder(Order order) {
        if (orderRepository.findById(order.getId()).isPresent()){
            orderRepository.delete(order);
            return true;
        }
        return false;
    }
}
