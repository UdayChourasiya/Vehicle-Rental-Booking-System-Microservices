package com.example.demo.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.BookingDto;

@FeignClient(name="booking-service")
public interface BookingClient {
	@GetMapping("/bookings/{id}")
	public BookingDto findBookingById(@PathVariable("id") int id);

}
