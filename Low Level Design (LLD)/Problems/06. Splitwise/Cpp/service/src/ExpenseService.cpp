#include "../include/ExpenseService.h"

std::shared_ptr<Expense> ExpenseService::createExpense(const std::shared_ptr<Expense>& expense) {
    expense->calculateSplit();

    const auto& userShare = expense->getUserShare();
    std::shared_ptr<User> paidBy = expense->getPaidBy();

    for (const auto& entry : userShare) {
        const std::shared_ptr<User>& user = entry.first;
        double amountOwed = entry.second;

        if (!user) {
            throw std::invalid_argument("Null user in userShare map");
        }

        if (user->getUserId() != paidBy->getUserId()) {
            user->updateBalanceSheet(paidBy->getUserId(), -amountOwed);
            paidBy->updateBalanceSheet(user->getUserId(), amountOwed);
        }
    }

    allExpenses.push_back(expense);
    return expense;
}

const std::vector<std::shared_ptr<Expense>>& ExpenseService::getAllExpenses() const {
    return allExpenses;
}
