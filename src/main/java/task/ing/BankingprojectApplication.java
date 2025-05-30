package task.ing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BankingprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingprojectApplication.class, args);
    }

}