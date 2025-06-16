package com.splitwise.splitwise.service;

import com.splitwise.splitwise.model.Expense;
import com.splitwise.splitwise.model.User;

import java.util.*;

public class ExpenseService {
    private List<Expense> allExpenses = new ArrayList<>();

    public Expense createExpense(Expense expense) {
        expense.calculateSplit();

        for (Map.Entry<User, Double> entry : expense.getUserShare().entrySet()) {
            User user = entry.getKey();
            if (user == null) {
                throw new IllegalArgumentException("Null user in userShare map");
            }
            if (!user.getUserId().equals(expense.getPaidBy().getUserId())) {
                user.updateBalanceSheet(expense.getPaidBy().getUserId(), -entry.getValue());
                expense.getPaidBy().updateBalanceSheet(user.getUserId(), entry.getValue());
            }
        }

        allExpenses.add(expense);
        return expense;
    }

    public List<Expense> getAllExpenses() {
        return allExpenses;
    }
}
