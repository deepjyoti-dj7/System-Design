#pragma once

#include "../enums/ExpenseType.h"
#include "User.h"
#include <string>
#include <vector>
#include <unordered_map>
#include <memory>
#include <chrono>

class Expense {
protected:
    std::string expenseId;
    std::string description;
    double amount;
    std::shared_ptr<User> paidBy;
    std::vector<std::shared_ptr<User>> participants;
    ExpenseType type;
    std::chrono::system_clock::time_point createdAt;
    std::unordered_map<std::shared_ptr<User>, double> userShare;

public:
    Expense(const std::string& expenseId,
            const std::string& description,
            double amount,
            std::shared_ptr<User> paidBy,
            const std::vector<std::shared_ptr<User>>& participants,
            ExpenseType type);

    virtual ~Expense() = default;

    virtual void calculateSplit() = 0;

    const std::string& getExpenseId() const;
    const std::string& getDescription() const;
    double getAmount() const;
    std::shared_ptr<User> getPaidBy() const;
    const std::vector<std::shared_ptr<User>>& getParticipants() const;
    ExpenseType getExpenseType() const;
    std::chrono::system_clock::time_point getCreatedAt() const;
    const std::unordered_map<std::shared_ptr<User>, double>& getUserShare() const;

protected:
    std::unordered_map<std::shared_ptr<User>, double>& getUserShareMutable();
};
