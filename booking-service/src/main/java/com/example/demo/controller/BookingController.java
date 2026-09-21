package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Client.CustomerClient;
import com.example.demo.Client.VehicleClient;
import com.example.demo.dao.BookingRepository;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.VehicleDto;
import com.example.demo.model.Booking;

import feign.FeignException;
@RequestMapping("/bookings")
@RestController
public class BookingController {
private BookingRepository bookingRepository;
private CustomerClient customerClient;
private VehicleClient vehicleClient;

public BookingController(BookingRepository bookingRepository, CustomerClient customerClient,
		VehicleClient vehicleClient) {
	super();
	this.bookingRepository = bookingRepository;
	this.customerClient = customerClient;
	this.vehicleClient = vehicleClient;
}

@PostMapping
public ResponseEntity<?> addBooking(@RequestParam("vehicleId")int VehicleId,@RequestParam("customerId")int CustomerId,@RequestBody Booking booking)
{
	try {
	CustomerDto customerDto=customerClient.findcustomerById(CustomerId);
	}catch(FeignException.NotFound e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("CustomerId  "+CustomerId+"  notFound");

	}
	try {
	VehicleDto vehicleDto=vehicleClient.findvehicleById(VehicleId);
	}catch(FeignException.NotFound e) 
	{	
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("VehicleId  "+VehicleId+"  notFound");
		
	}
	booking.setBookingDate(LocalDate.now());
	booking.setCustomerId(CustomerId);
	booking.setVehicleId(VehicleId);
	
	Booking booking1=bookingRepository.save(booking);
return new ResponseEntity<>(booking1,HttpStatus.CREATED);	
}

@GetMapping
public ResponseEntity<List<Booking>> findallBookings()
{
List<Booking> list= bookingRepository.findAll();
return ResponseEntity.ok(list);
}

@GetMapping("/{id}")
public ResponseEntity<Booking> findBookingById(@PathVariable("id") int id)
{
Booking booking=bookingRepository.findById(id).orElse(null);	
if(booking==null)
{
return ResponseEntity.notFound().build();	
}
return ResponseEntity.ok(booking);
}
@PutMapping
public ResponseEntity<Booking> updateBooking(@RequestBody Booking booking)
{
Booking booking1= bookingRepository.save(booking);
return ResponseEntity.ok(booking);
}
@DeleteMapping("/{id}")
public ResponseEntity<Booking> deleteBooking(@PathVariable("id") int id) 
{
	Booking booking=bookingRepository.findById(id).orElse(null);	
	if(booking==null)
	{
	return ResponseEntity.notFound().build();	
	}
bookingRepository.deleteById(id);	
return ResponseEntity.noContent().build();	
}

}
