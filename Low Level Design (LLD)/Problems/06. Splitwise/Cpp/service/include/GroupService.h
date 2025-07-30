#pragma once

#include "../../model/include/Group.h"
#include "../../model/include/User.h"
#include "../../model/include/Expense.h"
#include <unordered_map>
#include <vector>
#include <string>
#include <memory>

class GroupService {
private:
    std::unordered_map<std::string, std::shared_ptr<Group>> groups;

public:
    std::shared_ptr<Group> createGroup(const std::string& groupId, const std::string& name);

    void addUserToGroup(const std::string& groupId, const std::shared_ptr<User>& user);

    std::shared_ptr<Group> getGroup(const std::string& groupId) const;

    std::vector<std::shared_ptr<Expense>> getGroupExpenses(const std::string& groupId) const;
};
