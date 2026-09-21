package com.example.demo.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.VehicleDto;

@FeignClient(name="vehicle-service")
public interface VehicleClient {
@GetMapping("/vehicles/{id}")
public VehicleDto findvehicleById(@PathVariable("id") int id);
}
