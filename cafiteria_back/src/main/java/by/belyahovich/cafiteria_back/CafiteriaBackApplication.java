package by.belyahovich.cafiteria_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//(exclude = {SecurityAutoConfiguration.class })
@SpringBootApplication
public class CafiteriaBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafiteriaBackApplication.class, args);
    }
}


//user <-> order <-> pizza <- ingridient