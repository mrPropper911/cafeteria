package by.belyahovich.cafiteria_back.service.user;

import by.belyahovich.cafiteria_back.domain.Order;
import by.belyahovich.cafiteria_back.domain.User;

import java.util.List;

public interface UserService {

    User findUserById (long userId);
    List<User> allUsers();
    boolean saveUser(User user);
    boolean deleteUser(User user);
    List<Order> getAllOrdersByUser(User user);
}
