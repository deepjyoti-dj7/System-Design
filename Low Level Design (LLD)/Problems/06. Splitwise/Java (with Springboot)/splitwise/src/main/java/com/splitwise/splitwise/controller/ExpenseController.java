package com.splitwise.splitwise.controller;

import com.splitwise.splitwise.dto.ExpenseRequestDTO;
import com.splitwise.splitwise.model.*;
import com.splitwise.splitwise.service.ExpenseService;
import com.splitwise.splitwise.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService = new ExpenseService();
    private final UserService userService = new UserService();

    @PostMapping
    public ResponseEntity<String> createExpense(@RequestBody ExpenseRequestDTO dto) {
        User paidBy = userService.getUser(dto.getPaidBy());
        List<User> users = new ArrayList<>();
        for (String id : dto.getParticipants()) {
            users.add(userService.getUser(id));
        }
        Expense expense = null;
        switch (dto.getType().toUpperCase()) {
            case "EQUAL":
                expense = new EqualExpense(UUID.randomUUID().toString(), dto.getDescription(), dto.getAmount(), paidBy, users);
                break;
            case "EXACT":
                expense = new ExactExpense(UUID.randomUUID().toString(), dto.getDescription(), dto.getAmount(), paidBy, users, dto.getExactAmounts());
                break;
            case "PERCENTAGE":
                expense = new PercentageExpense(UUID.randomUUID().toString(), dto.getDescription(), dto.getAmount(), paidBy, users, dto.getPercentages());
                break;
        }
        expenseService.createExpense(expense);
        return ResponseEntity.ok("Expense created successfully");
    }
}