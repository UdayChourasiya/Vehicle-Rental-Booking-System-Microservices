package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedBackViewController {

    @GetMapping("/feedback")
    public String feedback() {
        return "feedback";   // templates/feedback.html
    }
}