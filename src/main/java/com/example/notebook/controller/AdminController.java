package com.example.notebook.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AdminController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/check")
    public String checkAdminAccess() {
        return "ADMIN access granted! You have entered the VIP area.";
    }

}
