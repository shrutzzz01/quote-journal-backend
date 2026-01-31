package com.quotejournal.controller;

import com.quotejournal.dto.AdminDashboardResponse;
import com.quotejournal.dto.AdminUserRequest;
import com.quotejournal.dto.UserResponse;
import com.quotejournal.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AdminDashboardResponse> getDashboard(){
        System.out.println("Current User Authorities: " +
                SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        AdminDashboardResponse dashboardData=adminService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
    @PutMapping("/updateRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse handleRoleChange(@RequestBody AdminUserRequest adminUserRequest){
        return adminService.changeUserRole(adminUserRequest);
    }
    @DeleteMapping("/deleteUser/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteUserByAdmin(@PathVariable("userId") Long userId) {
        adminService.deleteUserByAdmin(userId);

        // Explicitly return a JSON object. React LOVES JSON.
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        return ResponseEntity.ok(response);
    }
}
