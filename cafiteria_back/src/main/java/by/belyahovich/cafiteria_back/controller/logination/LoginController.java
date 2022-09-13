package by.belyahovich.cafiteria_back.controller.logination;

import by.belyahovich.cafiteria_back.domain.AccountCredentials;
import by.belyahovich.cafiteria_back.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public LoginController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping( value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials accountCredentials){
        UsernamePasswordAuthenticationToken creds =
                new UsernamePasswordAuthenticationToken(
                        accountCredentials.getUsername(),
                        accountCredentials.getPassword()
                );
        Authentication authentication = authenticationManager.authenticate(creds);

        String jwts = jwtService.getToken(authentication.getName());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .build();
    }
}
