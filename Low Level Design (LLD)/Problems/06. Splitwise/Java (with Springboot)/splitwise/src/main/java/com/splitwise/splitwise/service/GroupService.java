package com.splitwise.splitwise.service;

import com.splitwise.splitwise.model.Expense;
import com.splitwise.splitwise.model.Group;
import com.splitwise.splitwise.model.User;

import java.util.*;

public class GroupService {
    private Map<String, Group> groups = new HashMap<>();

    public Group createGroup(String groupId, String name) {
        Group group = new Group(groupId, name);
        groups.put(groupId, group);
        return group;
    }

    public void addUserToGroup(String groupId, User user) {
        Group group = groups.get(groupId);
        if (group != null) {
            group.addMember(user);
        }
    }

    public List<Expense> getGroupExpenses(String groupId) {
        Group group = groups.get(groupId);
        return group != null ? group.getExpenses() : new ArrayList<>();
    }
}
