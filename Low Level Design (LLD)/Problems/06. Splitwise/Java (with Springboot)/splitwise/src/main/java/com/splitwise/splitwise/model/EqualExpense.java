package com.splitwise.splitwise.model;

import com.splitwise.splitwise.model.enums.ExpenseType;

import java.util.*;

public class EqualExpense extends Expense {
    public EqualExpense(String expenseId, String description, double amount, User paidBy, List<User> participants) {
        super(expenseId, description, amount, paidBy, participants, ExpenseType.EQUAL);
    }

    @Override
    public void calculateSplit() {
        double splitAmount = amount / participants.size();
        for (User user : participants) {
            userShare.put(user, splitAmount);
        }
    }
}
