package com.splitwise.splitwise.model;

import java.util.*;

public class Group {
    private String groupId;
    private String name;
    private List<User> members = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();

    public Group() {}
    public Group(String groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public String getGroupId() { return groupId; }
    public String getName() { return name; }
    public List<User> getMembers() { return members; }
    public List<Expense> getExpenses() { return expenses; }

    public void addMember(User user) { members.add(user); }
    public void addExpense(Expense expense) { expenses.add(expense); }
}
