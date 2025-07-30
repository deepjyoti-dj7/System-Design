#include "../include/Group.h"

Group::Group(const std::string& groupId, const std::string& name)
    : groupId(groupId), name(name) {}

const std::string& Group::getGroupId() const {
    return groupId;
}

const std::string& Group::getName() const {
    return name;
}

const std::vector<std::shared_ptr<User>>& Group::getMembers() const {
    return members;
}

const std::vector<std::shared_ptr<Expense>>& Group::getExpenses() const {
    return expenses;
}

void Group::addMember(const std::shared_ptr<User>& user) {
    members.push_back(user);
}

void Group::addExpense(const std::shared_ptr<Expense>& expense) {
    expenses.push_back(expense);
}
