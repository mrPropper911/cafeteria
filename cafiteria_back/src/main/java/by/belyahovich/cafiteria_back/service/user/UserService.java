package by.belyahovich.cafiteria_back.service.user;

import by.belyahovich.cafiteria_back.domain.Order;
import by.belyahovich.cafiteria_back.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserById (long userId);
    List<User> allUsers();
    User createUser(User user);
    boolean deleteUser(User user);
    List<Order> getAllOrdersByUser(User user);

}
