#pragma once

#include "Expense.h"
#include <vector>
#include <memory>

class ExactExpense : public Expense {
private:
    std::vector<double> exactAmounts;

public:
    ExactExpense(const std::string& expenseId,
                 const std::string& description,
                 double amount,
                 std::shared_ptr<User> paidBy,
                 const std::vector<std::shared_ptr<User>>& participants,
                 const std::vector<double>& exactAmounts);

    void calculateSplit() override;
};
