#pragma once

#include "../../model/include/Expense.h"
#include "../../model/include/User.h"
#include <vector>
#include <memory>
#include <stdexcept>

class ExpenseService {
private:
    std::vector<std::shared_ptr<Expense>> allExpenses;

public:
    std::shared_ptr<Expense> createExpense(const std::shared_ptr<Expense>& expense);

    const std::vector<std::shared_ptr<Expense>>& getAllExpenses() const;
};
