package com.quotejournal.controller;

import com.quotejournal.dto.AdminDashboardResponse;
import com.quotejournal.dto.AdminUserRequest;
import com.quotejournal.dto.UserResponse;
import com.quotejournal.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService){
        this.adminService=adminService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboard(){
        AdminDashboardResponse dashboardData=adminService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
    @PutMapping("/updateRole")
    public UserResponse handleRoleChange(@RequestBody AdminUserRequest adminUserRequest){
        return adminService.changeUserRole(adminUserRequest);
    }
    @DeleteMapping("/deleteUser")
    public void deleteUserByAdmin(@PathVariable Long userId){
        adminService.deleteUserByAdmin(userId);
    }
}
