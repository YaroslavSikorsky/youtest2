package infrastructure;

import domain.User;
import domain.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication(scanBasePackages = {"domain", "infrastructure"})
@EnableJdbcRepositories(basePackages = "domain")
public class Core {
    public static void main(String[] args) {
        SpringApplication.run(Core.class, args);
    }


}