package test.springboard.repository;

import test.springboard.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByN(Long n);

    Optional<User> findById(String id);

    List<User> findAll();
}