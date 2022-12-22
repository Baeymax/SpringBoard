package test.springboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.springboard.repository.MemoryUserRepository;
import test.springboard.repository.UserRepository;
import test.springboard.service.UserService;

@Configuration
public class SpringConfig {

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

    @Bean
    UserRepository userRepository() {
        return new MemoryUserRepository();
    }
}
