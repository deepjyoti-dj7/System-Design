package com.splitwise.splitwise.model;

import com.splitwise.splitwise.model.enums.ExpenseType;

import java.util.*;

public class ExactExpense extends Expense {
    private List<Double> exactAmounts;

    public ExactExpense(String expenseId, String description, double amount, User paidBy, List<User> participants, List<Double> exactAmounts) {
        super(expenseId, description, amount, paidBy, participants, ExpenseType.EQUAL);
        this.exactAmounts = exactAmounts;
    }

    @Override
    public void calculateSplit() {
        for (int i = 0; i < participants.size(); i++) {
            userShare.put(participants.get(i), exactAmounts.get(i));
        }
    }
}