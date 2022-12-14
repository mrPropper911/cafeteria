package by.belyahovich.cafiteria_back.controller.logination;

import by.belyahovich.cafiteria_back.domain.User;
import by.belyahovich.cafiteria_back.service.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public List<User> userList() {
        return userService.allUsers();
    }

    @GetMapping("/admin/{id}")
    public Optional<User> getUser(@PathVariable long id){
        return userService.findUserById(id);
    }


}
