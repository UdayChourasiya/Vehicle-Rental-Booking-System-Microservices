package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VehicleViewController {

	
    @GetMapping("/vehicle")
    public String vehicle() {
        return "vehicle";   // templates/vehicle.html
    }
}