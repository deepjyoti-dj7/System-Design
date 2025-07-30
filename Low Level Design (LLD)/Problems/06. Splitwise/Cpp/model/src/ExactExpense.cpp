#include "../include/ExactExpense.h"

ExactExpense::ExactExpense(const std::string& expenseId,
                           const std::string& description,
                           double amount,
                           std::shared_ptr<User> paidBy,
                           const std::vector<std::shared_ptr<User>>& participants,
                           const std::vector<double>& exactAmounts)
    : Expense(expenseId, description, amount, paidBy, participants, ExpenseType::EXACT),
      exactAmounts(exactAmounts) {}

void ExactExpense::calculateSplit() {
    auto& userShareMap = getUserShareMutable();

    for (size_t i = 0; i < participants.size(); ++i) {
        userShareMap[participants[i]] = exactAmounts[i];
    }
}
