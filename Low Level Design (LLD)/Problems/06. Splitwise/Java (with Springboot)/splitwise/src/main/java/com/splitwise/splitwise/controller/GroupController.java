package com.splitwise.splitwise.controller;

import com.splitwise.splitwise.model.Expense;
import com.splitwise.splitwise.model.Group;
import com.splitwise.splitwise.model.User;
import com.splitwise.splitwise.service.GroupService;
import com.splitwise.splitwise.service.UserService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService = new GroupService();
    private final UserService userService = new UserService();

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        System.out.println("Received group: " + group.getGroupId() + ", " + group.getName());
        return ResponseEntity.ok(groupService.createGroup(group.getGroupId(), group.getName()));
    }

    @PostMapping("/{groupId}/addUser")
    public ResponseEntity<String> addUserToGroup(@PathVariable String groupId, @RequestParam String userId) {
        User user = userService.getUser(userId);
        groupService.addUserToGroup(groupId, user);
        return ResponseEntity.ok("User added to group");
    }

    @GetMapping("/{groupId}/expenses")
    public ResponseEntity<List<Expense>> getExpenses(@PathVariable String groupId) {
        return ResponseEntity.ok(groupService.getGroupExpenses(groupId));
    }
}