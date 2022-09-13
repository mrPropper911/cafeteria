package by.belyahovich.cafiteria_back.service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
    static final long EXPIRATIONTIME = 86400000; //1 day in ms
    static final String PREFIX = "Bearer";
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //Generate signed Jwt token
    public String getToken(String phone) {
        String token = Jwts.builder()
                .setSubject(phone)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(key)
                .compact();
        return token;
    }

    //Get a token from request Authorization header, verify a token and get phone
    public String getAuthUser(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token != null) {
            String user = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (user != null){
                return user;
            }
        }
        return null;
    }
}
