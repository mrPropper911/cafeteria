package by.belyahovich.cafiteria_back.controller.logination;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @GetMapping("/registration")
    public String registration(){
        return "qeee";
    }

}
