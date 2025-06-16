package model;

import model.enums.ExpenseType;

import java.util.*;

public abstract class Expense {
    protected String expenseId;
    protected String description;
    protected double amount;
    protected User paidBy;
    protected List<User> participants;
    protected ExpenseType type;
    protected Date createdAt;
    protected Map<User, Double> userShare;

    public Expense(String expenseId, String description, double amount, User paidBy, List<User> participants, ExpenseType type) {
        this.expenseId = expenseId;
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.participants = participants;
        this.type = type;
        this.createdAt = new Date();
        this.userShare = new HashMap<>();
    }

    public abstract void calculateSplit();

    public String getExpenseId() { return expenseId; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public User getPaidBy() { return paidBy; }
    public List<User> getParticipants() { return participants; }
    public ExpenseType getExpenseType() { return type; }
    public Date getCreatedAt() { return createdAt; }
    public Map<User, Double> getUserShare() { return userShare; }
}
