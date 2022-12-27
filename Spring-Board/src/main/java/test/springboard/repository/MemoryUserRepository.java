package test.springboard.repository;

import test.springboard.domain.User;

import java.util.*;

public class MemoryUserRepository implements UserRepository{
    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public User save(User user) {
        user.setIndex(++sequence);
        store.put(user.getIndex(), user);
        return user;
    }

    @Override
    public Optional<User> findByN(Long n) {
        return Optional.ofNullable(store.get(n));
    }

    @Override
    public Optional<User> findById(String id) {
        return store.values().stream()
                .filter(user -> user.getId().equals(id))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
