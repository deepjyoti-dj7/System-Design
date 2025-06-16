package com.splitwise.splitwise.service;

import com.splitwise.splitwise.model.User;

import java.util.*;

public class UserService {
    private Map<String, User> users = new HashMap<>();

    public User createUser(String userId, String name, String email, String phoneNumber) {
        User user =  new User(userId, name, email, phoneNumber);
        users.put(userId, user);
        return user;
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values()); // Assuming 'users' is a Map<String, User>
    }

}
