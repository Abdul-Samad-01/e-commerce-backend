package com.sellerbuyer.sellerbuyer.controller;

import com.sellerbuyer.sellerbuyer.payloads.dto.UserDto;
import com.sellerbuyer.sellerbuyer.service.AdminService;
import com.sellerbuyer.sellerbuyer.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Get a user by its id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        UserDto userDto = adminService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Get all user with pagination")
    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUser(@RequestParam(defaultValue = "0") int pageNo,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(adminService.getUsers(PaginationUtil.getPageable(pageNo, pageSize, null)));
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(adminService.addUser(userDto));
    }

    @Operation(summary = "Approve a user by its id")
    @PatchMapping("/{id}/approve")
    public ResponseEntity<Void> approveUser(@PathVariable long id) {
        adminService.approveUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deactivate a user by its id")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable long id) {
        adminService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activate a user by its id")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable long id) {
        adminService.activateUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a user based on their role")
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDto>> getUserByRole(@PathVariable("role") String role) {
        return ResponseEntity.ok(adminService.getUserByRole(role));
    }
}
