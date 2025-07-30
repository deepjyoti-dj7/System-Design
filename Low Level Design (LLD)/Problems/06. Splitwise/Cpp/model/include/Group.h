#pragma once

#include "User.h"
#include "Expense.h"
#include <string>
#include <vector>
#include <memory>

class Group {
private:
    std::string groupId;
    std::string name;
    std::vector<std::shared_ptr<User>> members;
    std::vector<std::shared_ptr<Expense>> expenses;

public:
    Group() = default;
    Group(const std::string& groupId, const std::string& name);

    const std::string& getGroupId() const;
    const std::string& getName() const;
    const std::vector<std::shared_ptr<User>>& getMembers() const;
    const std::vector<std::shared_ptr<Expense>>& getExpenses() const;

    void addMember(const std::shared_ptr<User>& user);
    void addExpense(const std::shared_ptr<Expense>& expense);
};
