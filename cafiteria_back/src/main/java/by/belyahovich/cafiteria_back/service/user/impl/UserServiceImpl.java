package by.belyahovich.cafiteria_back.service.user.impl;

import by.belyahovich.cafiteria_back.domain.Order;
import by.belyahovich.cafiteria_back.domain.User;
import by.belyahovich.cafiteria_back.repository.user.UserRepository;
import by.belyahovich.cafiteria_back.repository.user.UserRepositoryJpa;
import by.belyahovich.cafiteria_back.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRepositoryJpa userRepositoryJpa;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRepositoryJpa userRepositoryJpa) {
        this.userRepository = userRepository;
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public User findUserById(long userId) {
        Optional<User> userFromDB = userRepository.findById(userId);
        return userFromDB.orElse(new User());
    }

    @Override
    public List<User> allUsers() {
        return userRepositoryJpa.findAll();
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepositoryJpa.findUserByPhone(user.getPhone());

        if (userFromDB != null){
            return false;
        }

        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public List<Order> getAllOrdersByUser(User user) {
        return null;
    }
}
