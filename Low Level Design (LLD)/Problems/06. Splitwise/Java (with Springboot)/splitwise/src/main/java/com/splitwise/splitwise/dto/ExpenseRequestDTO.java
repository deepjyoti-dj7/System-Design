package com.splitwise.splitwise.dto;

import java.util.List;

public class ExpenseRequestDTO {
    private String type;
    private double amount;
    private String description;
    private String paidBy;
    private List<String> participants;
    private List<Double> exactAmounts;
    private List<Double> percentages;
    private String groupId;

    // Default constructor
    public ExpenseRequestDTO() {}

    // Full constructor
    public ExpenseRequestDTO(String type, double amount, String description, String paidBy,
                             List<String> participants, List<Double> exactAmounts,
                             List<Double> percentages, String groupId) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.paidBy = paidBy;
        this.participants = participants;
        this.exactAmounts = exactAmounts;
        this.percentages = percentages;
        this.groupId = groupId;
    }

    // Getters and Setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public List<Double> getExactAmounts() {
        return exactAmounts;
    }

    public void setExactAmounts(List<Double> exactAmounts) {
        this.exactAmounts = exactAmounts;
    }

    public List<Double> getPercentages() {
        return percentages;
    }

    public void setPercentages(List<Double> percentages) {
        this.percentages = percentages;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
