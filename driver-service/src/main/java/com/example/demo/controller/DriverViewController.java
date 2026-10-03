package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DriverViewController {

    @GetMapping("/driver")
    public String driver() {
        return "driver";   // templates/driver.html
    }
}