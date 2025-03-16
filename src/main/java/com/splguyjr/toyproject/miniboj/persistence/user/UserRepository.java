package com.splguyjr.toyproject.miniboj.persistence.user;

import com.splguyjr.toyproject.miniboj.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final UserStorage userStorage;
    private final List<User> userList;

    public UserRepository(UserStorage userStorage) {
        this.userStorage = userStorage;
        this.userList = userStorage.load();
    }

    public void insertUser(User user) {
        userList.add(user);
        userStorage.save(userList);
    }

    public User selectUserById(String id) {
        return userList.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public List<User> selectAllUsers() {
        return new ArrayList<>(userList);
    }

    public void deleteUser(User user) {
        userList.remove(user);
        userStorage.save(userList);
    }
}
