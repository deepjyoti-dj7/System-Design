#include "../include/GroupService.h"

std::shared_ptr<Group> GroupService::createGroup(const std::string& groupId, const std::string& name) {
    auto group = std::make_shared<Group>(groupId, name);
    groups[groupId] = group;
    return group;
}

void GroupService::addUserToGroup(const std::string& groupId, const std::shared_ptr<User>& user) {
    auto it = groups.find(groupId);
    if (it != groups.end() && it->second) {
        it->second->addMember(user);
    }
}

std::shared_ptr<Group> GroupService::getGroup(const std::string& groupId) const {
    auto it = groups.find(groupId);
    return it != groups.end() ? it->second : nullptr;
}

std::vector<std::shared_ptr<Expense>> GroupService::getGroupExpenses(const std::string& groupId) const {
    auto group = getGroup(groupId);
    return group ? group->getExpenses() : std::vector<std::shared_ptr<Expense>>{};
}
