package model;

import model.enums.ExpenseType;

import java.util.*;

public class PercentageExpense extends Expense {
    private List<Double> percentages;

    public PercentageExpense(String expenseId, String description, double amount, User paidBy, List<User> participants, List<Double> percentages) {
        super(expenseId, description, amount, paidBy, participants, ExpenseType.PERCENTAGE);
        this.percentages = percentages;
    }

    @Override
    public void calculateSplit() {
        for (int i = 0; i < participants.size(); i++) {
            double share = (percentages.get(i) / 100.0) * amount;
            userShare.put(participants.get(i), share);
        }
    }
}
