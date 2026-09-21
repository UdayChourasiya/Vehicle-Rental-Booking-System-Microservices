package com.example.demo.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.DriverDto;

@FeignClient(name="driver-service")
public interface DriverClient {
@GetMapping("/drivers/{id}")
public DriverDto findDriverById(@PathVariable("id") int id);
}
