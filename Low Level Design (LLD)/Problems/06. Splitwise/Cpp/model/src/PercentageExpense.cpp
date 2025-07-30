#include "../include/PercentageExpense.h"

PercentageExpense::PercentageExpense(const std::string& expenseId,
                                     const std::string& description,
                                     double amount,
                                     std::shared_ptr<User> paidBy,
                                     const std::vector<std::shared_ptr<User>>& participants,
                                     const std::vector<double>& percentages)
    : Expense(expenseId, description, amount, paidBy, participants, ExpenseType::PERCENTAGE),
      percentages(percentages) {}

void PercentageExpense::calculateSplit() {
    auto& userShareMap = getUserShareMutable();

    for (size_t i = 0; i < participants.size(); ++i) {
        double share = (percentages[i] / 100.0) * amount;
        userShareMap[participants[i]] = share;
    }
}
