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

import com.example.demo.Client.BookingClient;
import com.example.demo.Client.CustomerClient;
import com.example.demo.Client.TripClient;
import com.example.demo.dao.FeedBackRepository;
import com.example.demo.dto.BookingDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.TripDto;
import com.example.demo.model.FeedBack;

import feign.FeignException;
@RequestMapping("/feedbacks")
@RestController
public class FeedBackController {
private FeedBackRepository feedBackRepository;
private BookingClient bookingClient;
private CustomerClient customerClient;
private TripClient tripClient;
public FeedBackController(FeedBackRepository feedBackRepository, BookingClient bookingClient,
		CustomerClient customerClient, TripClient tripClient) {
	super();
	this.feedBackRepository = feedBackRepository;
	this.bookingClient = bookingClient;
	this.customerClient = customerClient;
	this.tripClient = tripClient;
}

@PostMapping 
public ResponseEntity<?> addfeedBack(@RequestBody FeedBack feedBack,@RequestParam("tripId")int TripId,@RequestParam("bookingId")int BookingId,@RequestParam("customerId")int CustomerId)
{
	
	try{
		CustomerDto customerDto=customerClient.findcustomerById(CustomerId);
	}
	catch(Exception e) {	
return ResponseEntity.status(HttpStatus.NOT_FOUND)
		.body("CustomerId  "+CustomerId+"  Not Found");

		
	}
	try {
	TripDto tripDto=tripClient.findTripById(TripId);
	}
	catch(FeignException.NotFound e)
	{
	return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("TripId   "+TripId+"  Not Found");

	}
	try {
	BookingDto bookingDto=bookingClient.findBookingById(BookingId);
	}
	catch(FeignException.NotFound e)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("BookingId  "+BookingId+"  Not Found");
	}
	
	feedBack.setBookingId(BookingId);
	feedBack.setCustomerId(CustomerId);
	feedBack.setTripId(TripId);
	feedBack.setFeedbackDate(LocalDate.now());
	
	FeedBack feedBack1=feedBackRepository.save(feedBack);
	
return new ResponseEntity<>(feedBack1,HttpStatus.CREATED);	
}

@GetMapping
public ResponseEntity<List<FeedBack>> findAllFeedBack()
{
	List<FeedBack> list =feedBackRepository.findAll();
return ResponseEntity.ok(list)	;
}

@GetMapping("/{id}")
public ResponseEntity<?> findfeedBackById(@PathVariable("id")int id)
{

		FeedBack feedback=feedBackRepository.findById(id).orElse(null);	
		if(feedback==null)
		{
return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id  "+id+"  Not Found");
				
		}
		return ResponseEntity.ok(feedback)	;
}
@PutMapping
public ResponseEntity<?> UpdateFeedBack(@PathVariable("id")int id,@RequestBody FeedBack feedBack)
{
	FeedBack feedback=feedBackRepository.findById(id).orElse(null);	
	if(feedback==null)
	{
return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id "+id+" Not Found");
			
	}
	
		FeedBack feedBack1=feedBackRepository.save(feedBack);	
		return ResponseEntity.ok(feedBack1);
}

@DeleteMapping("/{id}")
public ResponseEntity<?> DeleteFeedBack(@PathVariable("id")int id)
{
	FeedBack feedback=feedBackRepository.findById(id).orElse(null);	
	if(feedback==null)
	{
return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id "+id+" Not Found");
			
	}
	feedBackRepository.deleteById(id);
return ResponseEntity.noContent().build();
}
}
