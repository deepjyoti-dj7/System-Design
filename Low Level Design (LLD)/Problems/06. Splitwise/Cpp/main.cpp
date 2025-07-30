#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <unordered_map>
#include <limits>
#include <random>
#include <chrono>

#include "./service/include/UserService.h"
#include "./service/include/GroupService.h"
#include "./service/include/ExpenseService.h"
#include "./model/include/EqualExpense.h"
#include "./model/include/ExactExpense.h"
#include "./model/include/PercentageExpense.h"

using namespace std;

UserService userService;
GroupService groupService;
ExpenseService expenseService;

string generateUUID() {
    static random_device rd;
    static mt19937 gen(rd());
    static uniform_int_distribution<> dis(0, 15);
    static const char* hex = "0123456789abcdef";

    string uuid = "";
    for (int i = 0; i < 32; ++i) {
        uuid += hex[dis(gen)];
        if (i == 7 || i == 11 || i == 15 || i == 19)
            uuid += '-';
    }
    return uuid;
}

void createUser() {
    string id, name, email, phone;
    cout << "User ID: ";
    getline(cin, id);
    cout << "Name: ";
    getline(cin, name);
    cout << "Email: ";
    getline(cin, email);
    cout << "Phone: ";
    getline(cin, phone);

    userService.createUser(id, name, email, phone);
    cout << "User created." << endl;
}

void createGroup() {
    string groupId, name;
    cout << "Group ID: ";
    getline(cin, groupId);
    cout << "Group Name: ";
    getline(cin, name);

    groupService.createGroup(groupId, name);
    cout << "Group created." << endl;
}

void addUserToGroup() {
    string groupId, userId;
    cout << "Group ID: ";
    getline(cin, groupId);
    cout << "User ID: ";
    getline(cin, userId);

    auto user = userService.getUser(userId);
    if (user) {
        groupService.addUserToGroup(groupId, user);
        cout << "User added to group." << endl;
    } else {
        cout << "User not found." << endl;
    }
}

void createExpense() {
    string groupId, desc, paidById, type;
    double amount;

    cout << "Group ID: ";
    getline(cin, groupId);

    auto group = groupService.getGroup(groupId);
    if (!group) {
        cout << "Group not found." << endl;
        return;
    }

    cout << "Description: ";
    getline(cin, desc);
    cout << "Amount: ";
    cin >> amount;
    cin.ignore();

    cout << "Paid by (userId): ";
    getline(cin, paidById);

    cout << "Enter expense type (EQUAL, EXACT, PERCENTAGE): ";
    getline(cin, type);

    vector<shared_ptr<User>> participants;
    for (const auto& user : group->getMembers()) {
        cout << "Include " << user->getName() << "? (y/n): ";
        string input;
        getline(cin, input);
        if (input == "y" || input == "Y") {
            participants.push_back(user);
        }
    }

    vector<double> amounts;
    if (type == "EXACT" || type == "PERCENTAGE") {
        for (const auto& user : participants) {
            double val;
            cout << (type == "EXACT" ? "Amount" : "Percentage") << " for " << user->getName() << ": ";
            cin >> val;
            cin.ignore();
            amounts.push_back(val);
        }
    }

    auto paidBy = userService.getUser(paidById);
    if (!paidBy) {
        cout << "Invalid user ID." << endl;
        return;
    }

    string expenseId = generateUUID();
    shared_ptr<Expense> expense;

    if (type == "EQUAL") {
        expense = make_shared<EqualExpense>(expenseId, desc, amount, paidBy, participants);
    } else if (type == "EXACT") {
        expense = make_shared<ExactExpense>(expenseId, desc, amount, paidBy, participants, amounts);
    } else if (type == "PERCENTAGE") {
        expense = make_shared<PercentageExpense>(expenseId, desc, amount, paidBy, participants, amounts);
    } else {
        cout << "Invalid expense type." << endl;
        return;
    }

    expenseService.createExpense(expense);
    group->addExpense(expense);

    cout << "Expense recorded." << endl;
}

void showGroupExpenses() {
    string groupId;
    cout << "Group ID: ";
    getline(cin, groupId);

    auto expenses = groupService.getGroupExpenses(groupId);
    for (const auto& e : expenses) {
        cout << "\n[" << static_cast<int>(e->getExpenseType()) << "] " << e->getDescription()
             << " - $" << e->getAmount() << endl;
        for (const auto& entry : e->getUserShare()) {
            cout << "  " << entry.first->getName() << " owes: $" << entry.second << endl;
        }
    }
}

void showUserBalanceSheet() {
    string userId;
    cout << "User ID: ";
    getline(cin, userId);

    auto user = userService.getUser(userId);
    if (!user) {
        cout << "User not found." << endl;
        return;
    }

    cout << "Balance Sheet for " << user->getName() << ":\n";
    for (const auto& entry : user->getBalanceSheet()) {
        cout << "  Owes to " << entry.first << ": " << entry.second << endl;
    }
}

int main() {
    while (true) {
        cout << "\n--- Splitwise Menu ---\n";
        cout << "1. Create User\n";
        cout << "2. Create Group\n";
        cout << "3. Add User to Group\n";
        cout << "4. Create Expense\n";
        cout << "5. Show Group Expenses\n";
        cout << "6. Show User Balance Sheet\n";
        cout << "0. Exit\n";
        cout << "Choose: ";

        int choice;
        cin >> choice;
        cin.ignore(numeric_limits<streamsize>::max(), '\n'); // Clear input buffer

        switch (choice) {
            case 1: createUser(); break;
            case 2: createGroup(); break;
            case 3: addUserToGroup(); break;
            case 4: createExpense(); break;
            case 5: showGroupExpenses(); break;
            case 6: showUserBalanceSheet(); break;
            case 0:
                cout << "Goodbye!" << endl;
                return 0;
            default:
                cout << "Invalid option." << endl;
        }
    }
}
