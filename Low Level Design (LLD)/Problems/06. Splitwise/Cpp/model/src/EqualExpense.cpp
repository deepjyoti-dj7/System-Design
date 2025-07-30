#include "../include/EqualExpense.h"

EqualExpense::EqualExpense(const std::string& expenseId,
                           const std::string& description,
                           double amount,
                           std::shared_ptr<User> paidBy,
                           const std::vector<std::shared_ptr<User>>& participants)
    : Expense(expenseId, description, amount, paidBy, participants, ExpenseType::EQUAL) {}

void EqualExpense::calculateSplit() {
    double splitAmount = amount / participants.size();
    auto& userShareMap = getUserShareMutable();

    for (const auto& user : participants) {
        userShareMap[user] = splitAmount;
    }
}
