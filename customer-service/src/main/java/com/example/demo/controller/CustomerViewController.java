package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerViewController {

    @GetMapping({"/", "/dashboard"})
    public String dashboard() {
        return "dashboard";   // templates/dashboard.html
    }

    @GetMapping("/customer")
    public String customer() {
        return "customers";    // templates/customer.html
    }
}