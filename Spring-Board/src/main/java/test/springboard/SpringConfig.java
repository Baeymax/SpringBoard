package test.springboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.springboard.repository.MysqlUserRepository;
import test.springboard.repository.UserRepository;
import test.springboard.service.UserService;

import javax.sql.DataSource;


@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

    @Bean
    UserRepository userRepository() {
        return new MysqlUserRepository(dataSource);
    }
}
