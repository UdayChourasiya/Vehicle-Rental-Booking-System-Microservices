package com.example.demo.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.TripDto;

@FeignClient(name="trip-service")
public interface TripClient {
	@GetMapping("/trips/{id}")
	public TripDto findTripById(@PathVariable("id")int id);
	

}
