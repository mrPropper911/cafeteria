package by.belyahovich.cafiteria_back.controller.logination;

import by.belyahovich.cafiteria_back.domain.User;
import by.belyahovich.cafiteria_back.service.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String userList() {
        StringBuilder outTest = new StringBuilder();
        for (User user : userService.allUsers()) {
            outTest.append(user);
        }
        return outTest.toString();
    }
}
