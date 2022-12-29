package test.springboard.service;

import test.springboard.domain.User;
import test.springboard.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Long join(User user){
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getUserno();
    }
    private void validateDuplicateUser(User user){
        userRepository.findById(user.getId())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 등록된 아이디입니다.");
                });
    }
    public List<User> findUsers() {
        return userRepository.findAll();
    }
    public Optional<User> findOne(Long userN){
        return userRepository.findByN(userN);
    }
}
