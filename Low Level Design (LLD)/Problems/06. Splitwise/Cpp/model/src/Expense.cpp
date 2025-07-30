#include "../include/Expense.h"

Expense::Expense(const std::string& expenseId,
                 const std::string& description,
                 double amount,
                 std::shared_ptr<User> paidBy,
                 const std::vector<std::shared_ptr<User>>& participants,
                 ExpenseType type)
    : expenseId(expenseId),
      description(description),
      amount(amount),
      paidBy(paidBy),
      participants(participants),
      type(type),
      createdAt(std::chrono::system_clock::now()) {}

const std::string& Expense::getExpenseId() const {
    return expenseId;
}

const std::string& Expense::getDescription() const {
    return description;
}

double Expense::getAmount() const {
    return amount;
}

std::shared_ptr<User> Expense::getPaidBy() const {
    return paidBy;
}

const std::vector<std::shared_ptr<User>>& Expense::getParticipants() const {
    return participants;
}

ExpenseType Expense::getExpenseType() const {
    return type;
}

std::chrono::system_clock::time_point Expense::getCreatedAt() const {
    return createdAt;
}

const std::unordered_map<std::shared_ptr<User>, double>& Expense::getUserShare() const {
    return userShare;
}

std::unordered_map<std::shared_ptr<User>, double>& Expense::getUserShareMutable() {
    return userShare;
}
