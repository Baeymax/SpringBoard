package test.springboard.repository;

import test.springboard.domain.User;
import test.springboard.domain.UserCheck;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(String id);

    User login(UserCheck usercheck);

}
