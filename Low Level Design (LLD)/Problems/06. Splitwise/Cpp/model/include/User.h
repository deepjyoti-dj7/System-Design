#pragma once

#include <string>
#include <unordered_map>

class User {
private:
    std::string userId;
    std::string name;
    std::string email;
    std::string phoneNumber;
    std::unordered_map<std::string, double> balanceSheet;

public:
    User(const std::string& userId,
         const std::string& name,
         const std::string& email,
         const std::string& phoneNumber);

    const std::string& getUserId() const;
    const std::string& getName() const;
    const std::string& getEmail() const;
    const std::string& getPhoneNumber() const;
    const std::unordered_map<std::string, double>& getBalanceSheet() const;

    void updateBalanceSheet(const std::string& otherUserId, double amount);

    // For use in unordered_map keys
    bool operator==(const User& other) const;
};

// Optional: hash specialization for unordered_map if needed
namespace std {
    template<>
    struct hash<User> {
        std::size_t operator()(const User& user) const {
            return std::hash<std::string>()(user.getUserId());
        }
    };
}
