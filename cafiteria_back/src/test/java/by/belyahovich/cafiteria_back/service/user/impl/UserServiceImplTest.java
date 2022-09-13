package by.belyahovich.cafiteria_back.service.user.impl;

import by.belyahovich.cafiteria_back.config.ResourceNotFoundException;
import by.belyahovich.cafiteria_back.domain.Order;
import by.belyahovich.cafiteria_back.domain.User;
import by.belyahovich.cafiteria_back.repository.role.RoleRepositoryJpa;
import by.belyahovich.cafiteria_back.repository.user.UserRepository;
import by.belyahovich.cafiteria_back.repository.user.UserRepositoryJpa;
import by.belyahovich.cafiteria_back.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Unit-level testing for UserService")
class UserServiceImplTest {
    private static final long EXIST_USER_ID = 1L;
    private static final long EXIST_USER_SECOND_ID = 2L;

    private UserService userService;
    private UserRepository userRepository;
    private UserRepositoryJpa userRepositoryJpa;
    private RoleRepositoryJpa roleRepositoryJpa;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private User user;

    @BeforeEach
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        userRepositoryJpa = Mockito.mock(UserRepositoryJpa.class);
        roleRepositoryJpa = Mockito.mock(RoleRepositoryJpa.class);
        bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, userRepositoryJpa, roleRepositoryJpa);

        user = new User();
        user.setId(EXIST_USER_ID);
        user.setUsername("Vilat");
        user.setSurname("Volochai");
        user.setLocation("Kiev");
        user.setPhone(375334423863L);
        user.setEmail("vilat@gmail.com");
        user.setPassword("qwer4312");
        user.setListOrders(Collections.emptyList());
    }

    @Test
    public void findUserById_WithExistingUser_shouldPropperlyFindUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> actualUser = userService.findUserById(EXIST_USER_ID);

        assertAll(
                () -> assertThat(actualUser).isNotEmpty(),
                () -> assertThat(actualUser).isEqualTo(Optional.of(user))
        );
    }

    @Test
    public void findUserById_WithNonExsistingUser_shouldThrowUsernameNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.findUserById(user.getId()));
    }

    @Test
    public void allUsers_WithTwoEntity_shouldFindAllUsers() {
        //given
        User userSecond = new User();
        userSecond.setId(EXIST_USER_SECOND_ID);
        userSecond.setUsername("Inna");
        userSecond.setSurname("Loshick");
        userSecond.setLocation("litba");
        userSecond.setPhone(373314423863L);
        userSecond.setEmail("inna@mail.com");
        userSecond.setPassword("eqq22qq");
        userSecond.setListOrders(Collections.emptyList());

        List<User> expectedUserList = Arrays.asList(user, userSecond);

        //when
        when(userRepositoryJpa.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.allUsers();

        //then
        assertThat(actualUserList).hasSize(2);
    }

    @Test
    public void createUser_IfUserExist_shouldNotCreatedUser() {
        when(userRepositoryJpa.findUserByUsername(anyString())).thenReturn(user);

        assertThrows(ResourceNotFoundException.class,
                () -> userService.createUser(user));
    }

    @Test
    public void createUser_IfUserNotExist_shouldPropperlyCreateNewUser() {
        when(userRepositoryJpa.findUserByUsername(anyString())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("12r9743143pfpbu1i43f");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User actualUser = userService.createUser(user);

        //then
        assertAll(
                () -> assertThat(actualUser).isNotNull(),
                () -> assertThat(actualUser).isEqualTo(user),
                () -> verify(userRepository).save(user)
        );
    }

    @Test
    public void deleteUser_IfUserExist_shouldPropperlyDeleteUser(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        boolean checkDelete = userService.deleteUser(user);

        assertThat(checkDelete).isTrue();
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void deleteUser_IfUserNotExist_shouldReturnFalse(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean checkDelete = userService.deleteUser(user);

        assertThat(checkDelete).isFalse();
        verify(userRepository, times(0)).delete(user);
    }

    @Test
    public void getAllOrdersByUser_IfUserExist_shouldPropperlyGetAllOrders(){
        //given
        Date date = new Date();
        Order order1 = new Order(1L, date, "Minsk", "Like");
        Order order2= new Order(2L, date, "London", "Dislike");
        List<Order> orderList = Arrays.asList(order1, order2);

        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepositoryJpa.getAllOrdersByUserId(anyLong())).thenReturn(orderList);
        List<Order> actual = userService.getAllOrdersByUser(user);

        //then
        assertAll(
                () -> assertThat(actual).hasSize(2),
                () -> assertThat(actual).isEqualTo(orderList),
                () -> verify(userRepositoryJpa, times(1)).getAllOrdersByUserId(user.getId())
        );
    }

    @Test
    public void getAllOrdersByUser_IfUserNotExist_shouldPropperlyGetUsernameNotFoundException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userService.getAllOrdersByUser(user)
        );
    }
}