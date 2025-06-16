package model;

import java.util.*;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private Map<String, Double> balanceSheet = new HashMap<>();

    public User(String userId, String name, String email, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public Map<String, Double> getBalanceSheet() { return balanceSheet; }

    public void updateBalanceSheet(String userId, double amount) {
        balanceSheet.put(userId, balanceSheet.getOrDefault(userId, 0.0) + amount);
    }
}
