import java.util.*;

import model.*;
import service.*;

public class Main {
	private static final Scanner scanner = new Scanner(System.in);
	private static final UserService userService = new UserService();
	private static final GroupService groupService = new GroupService();
	private static final ExpenseService expenseService = new ExpenseService();

	public static void main(String[] args) {
		while (true) {
			System.out.println("\n--- Splitwise Menu ---");
			System.out.println("1. Create User");
			System.out.println("2. Create Group");
			System.out.println("3. Add User to Group");
			System.out.println("4. Create Expense");
			System.out.println("5. Show Group Expenses");
			System.out.println("6. Show User Balance Sheet");
			System.out.println("0. Exit");
			System.out.print("Choose: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
				case 1 -> createUser();
				case 2 -> createGroup();
				case 3 -> addUserToGroup();
				case 4 -> createExpense();
				case 5 -> showGroupExpenses();
				case 6 -> showUserBalanceSheet();
				case 0 -> {
					System.out.println("Goodbye!");
					return;
				}
				default -> System.out.println("Invalid option.");
			}
		}
	}

	private static void createUser() {
		System.out.print("User ID: ");
		String id = scanner.nextLine();
		System.out.print("Name: ");
		String name = scanner.nextLine();
		System.out.print("Email: ");
		String email = scanner.nextLine();
		System.out.print("Phone: ");
		String phone = scanner.nextLine();
		userService.createUser(id, name, email, phone);
		System.out.println("User created.");
	}

	private static void createGroup() {
		System.out.print("Group ID: ");
		String groupId = scanner.nextLine();
		System.out.print("Group Name: ");
		String name = scanner.nextLine();
		groupService.createGroup(groupId, name);
		System.out.println("Group created.");
	}

	private static void addUserToGroup() {
		System.out.print("Group ID: ");
		String groupId = scanner.nextLine();
		System.out.print("User ID: ");
		String userId = scanner.nextLine();
		User user = userService.getUser(userId);
		if (user != null) {
			groupService.addUserToGroup(groupId, user);
			System.out.println("User added to group.");
		} else {
			System.out.println("User not found.");
		}
	}

	private static void createExpense() {
		System.out.print("Group ID: ");
		String groupId = scanner.nextLine();
		System.out.print("Description: ");
		String desc = scanner.nextLine();
		System.out.print("Amount: ");
		double amount = scanner.nextDouble();
		scanner.nextLine();
		System.out.print("Paid by (userId): ");
		String paidById = scanner.nextLine();

		System.out.print("Enter expense type (EQUAL, EXACT, PERCENTAGE): ");
		String type = scanner.nextLine().toUpperCase();

		Group group = groupService.createGroup(groupId, groupId);
		List<User> participants = new ArrayList<>();
		for (User user : group.getMembers()) {
			System.out.print("Include " + user.getName() + "? (y/n): ");
			if (scanner.nextLine().equalsIgnoreCase("y")) {
				participants.add(user);
			}
		}

		List<Double> amounts = null;
		if (type.equals("EXACT")) {
			amounts = new ArrayList<>();
			for (User user : participants) {
				System.out.print("Amount for " + user.getName() + ": ");
				amounts.add(scanner.nextDouble());
				scanner.nextLine();
			}
		} else if (type.equals("PERCENTAGE")) {
			amounts = new ArrayList<>();
			for (User user : participants) {
				System.out.print("Percentage for " + user.getName() + ": ");
				amounts.add(scanner.nextDouble());
				scanner.nextLine();
			}
		}

		User paidBy = userService.getUser(paidById);
		Expense expense = null;
		String expenseId = UUID.randomUUID().toString();

		switch (type) {
			case "EQUAL" ->
					expense = new EqualExpense(expenseId, desc, amount, paidBy, participants);
			case "EXACT" ->
					expense = new ExactExpense(expenseId, desc, amount, paidBy, participants, amounts);
			case "PERCENTAGE" ->
					expense = new PercentageExpense(expenseId, desc, amount, paidBy, participants, amounts);
			default -> System.out.println("Invalid expense type.");
		}

		if (expense != null) {
			expenseService.createExpense(expense);
			groupService.createGroup(groupId, "").addExpense(expense);
			System.out.println("Expense recorded.");
		}
	}

	private static void showGroupExpenses() {
		System.out.print("Group ID: ");
		String groupId = scanner.nextLine();
		List<Expense> expenses = groupService.getGroupExpenses(groupId);
		for (Expense e : expenses) {
			System.out.println("\n[" + e.getExpenseType() + "] " + e.getDescription() + " - $" + e.getAmount());
			for (Map.Entry<User, Double> entry : e.getUserShare().entrySet()) {
				System.out.println("  " + entry.getKey().getName() + " owes: $" + entry.getValue());
			}
		}
	}

	private static void showUserBalanceSheet() {
		System.out.print("User ID: ");
		String userId = scanner.nextLine();
		User user = userService.getUser(userId);
		if (user != null) {
			System.out.println("Balance Sheet for " + user.getName() + ":");
			for (Map.Entry<String, Double> entry : user.getBalanceSheet().entrySet()) {
				System.out.printf("  Owes to %s: %.2f%n", entry.getKey(), entry.getValue());
			}
		} else {
			System.out.println("User not found.");
		}
	}
}
