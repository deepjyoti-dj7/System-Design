#pragma once

#include "Expense.h"
#include <memory>
#include <vector>

class EqualExpense : public Expense {
public:
    EqualExpense(const std::string& expenseId,
                 const std::string& description,
                 double amount,
                 std::shared_ptr<User> paidBy,
                 const std::vector<std::shared_ptr<User>>& participants);

    void calculateSplit() override;
};
