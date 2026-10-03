package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingViewController {

    @GetMapping("/booking")
    public String booking() {
        return "booking";   // templates/booking.html
    }
}