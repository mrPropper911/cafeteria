package by.belyahovich.cafiteria_back.controller.logination;

import by.belyahovich.cafiteria_back.domain.User;
import by.belyahovich.cafiteria_back.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<User> addUser(@RequestBody User user) throws URISyntaxException {
        User newUserToSave = userService.createUser(user);
        return ResponseEntity.created(new URI("/admin/" + newUserToSave.getId())).body(newUserToSave);
    }

}
