package by.belyahovich.cafiteria_back.service.user.impl;

import by.belyahovich.cafiteria_back.config.ResourceNotFoundException;
import by.belyahovich.cafiteria_back.domain.Order;
import by.belyahovich.cafiteria_back.domain.Role;
import by.belyahovich.cafiteria_back.domain.User;
import by.belyahovich.cafiteria_back.repository.role.RoleRepositoryJpa;
import by.belyahovich.cafiteria_back.repository.user.UserRepository;
import by.belyahovich.cafiteria_back.repository.user.UserRepositoryJpa;
import by.belyahovich.cafiteria_back.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserRepositoryJpa userRepositoryJpa;
    private final RoleRepositoryJpa roleRepositoryJpa;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRepositoryJpa userRepositoryJpa, RoleRepositoryJpa roleRepositoryJpa) {
        this.userRepository = userRepository;
        this.userRepositoryJpa = userRepositoryJpa;
        this.roleRepositoryJpa = roleRepositoryJpa;
    }

    @Override
    public Optional<User> findUserById(long userId) {
        return Optional.ofNullable(userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + userId + " not found")
        ));
    }

    @Override
    public List<User> allUsers() {
        return userRepositoryJpa.findAll();
    }

    @Override
    public User createUser(User user) {
        User userFromDB = userRepositoryJpa.findUserByUsername(user.getUsername());

        if (userFromDB != null) {
            throw new ResourceNotFoundException("Current user with id " + user.getId() + " exist");
        }

        //user.setSetRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        roleRepositoryJpa.save(new Role("ROLE_USER", user));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            userRepository.delete(user);
            return true;
        }
        return false ;
    }

    @Override
    public List<Order> getAllOrdersByUser(User user) {
        userRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + user.getId() + "not found"));
        return userRepositoryJpa.getAllOrdersByUserId(user.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositoryJpa.findUserByUsername(username);
        UserBuilder userBuilder;
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
//        else {
//            userBuilder = org.springframework.security.core.userdetails.
//                    User.withUsername(username);
//            userBuilder.password(user.getPassword());
//            userBuilder.roles(roleRepositoryJpa.findRoleByUserId(user.getId()).getName());
//        }
//        return userBuilder.build();
        user.setSetRoles(Collections.singleton(roleRepositoryJpa.findRoleByUserId(user.getId())));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isEnabled(),
                true, true, true,
                user.getAuthorities());
    }

}
