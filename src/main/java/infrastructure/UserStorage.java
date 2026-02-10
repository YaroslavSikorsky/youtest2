package infrastructure;

import domain.User;
import domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserStorage {

    private final UserRepository userRepository;

    public UserStorage(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void addUser(UUID id, String email, String password) {
       // User user = new User(email, password);
        userRepository.saveUser(id, email, password);
    }

    public User getUser(String email) {
        User user = userRepository.getUser(email);
        System.out.println(user);
       return user;
    }

}
