package service;

import models.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> users = new HashMap<>();

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }

    public User getUserById(String id) {
        return users.get(id);
    }
}
