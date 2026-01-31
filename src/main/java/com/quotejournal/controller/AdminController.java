package com.quotejournal.controller;

import com.quotejournal.dto.AdminDashboardResponse;
import com.quotejournal.dto.AdminUserRequest;
import com.quotejournal.dto.UserResponse;
import com.quotejournal.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService){
        this.adminService=adminService;
    }
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboard(){
        System.out.println("Current User Authorities: " +
                SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        AdminDashboardResponse dashboardData=adminService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
    @PutMapping("/updateRole")
    public UserResponse handleRoleChange(@RequestBody AdminUserRequest adminUserRequest){
        return adminService.changeUserRole(adminUserRequest);
    }
    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUserByAdmin(@PathVariable Long userId){
        adminService.deleteUserByAdmin(userId);
    }
}
