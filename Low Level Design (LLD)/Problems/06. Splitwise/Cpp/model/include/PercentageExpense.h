#pragma once

#include "Expense.h"
#include <vector>
#include <memory>

class PercentageExpense : public Expense {
private:
    std::vector<double> percentages;

public:
    PercentageExpense(const std::string& expenseId,
                      const std::string& description,
                      double amount,
                      std::shared_ptr<User> paidBy,
                      const std::vector<std::shared_ptr<User>>& participants,
                      const std::vector<double>& percentages);

    void calculateSplit() override;
};
