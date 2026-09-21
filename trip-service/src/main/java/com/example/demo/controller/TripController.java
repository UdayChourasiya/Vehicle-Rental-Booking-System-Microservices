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

import com.example.demo.Client.DriverClient;
import com.example.demo.Client.VehicleClient;
import com.example.demo.dao.TripRepository;
import com.example.demo.dto.DriverDto;
import com.example.demo.dto.VehicleDto;
import com.example.demo.model.Trip;

import feign.FeignException;
@RequestMapping("/trips")
@RestController
public class TripController {
private TripRepository tripRepository;
private VehicleClient vehicleClient;
private DriverClient driverClient;



public TripController(TripRepository tripRepository, VehicleClient vehicleClient, DriverClient driverClient) {
	super();
	this.tripRepository = tripRepository;
	this.vehicleClient = vehicleClient;
	this.driverClient = driverClient;
}
@PostMapping
public ResponseEntity<?> addTrip(@RequestParam("vehicleId")int VehicleId,@RequestParam("driverId")int DriverId, @RequestBody Trip trip)
{
	try {
	DriverDto driverDto=driverClient.findDriverById(DriverId);
	}
	catch(FeignException.NotFound e)
	{
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DriverId  "+DriverId+"  notFound");

	}
	try {
	VehicleDto vehicleDto=vehicleClient.findvehicleById(VehicleId);
	}
	catch(FeignException.NotFound e)
	{
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("VehicleId  "+VehicleId+"  notFound");

	}
trip.setDriverId(DriverId);	
trip.setDueDate(LocalDate.now());
trip.setVehicleId(VehicleId);
Trip trip1=tripRepository.save(trip);
return new ResponseEntity<>(trip1,HttpStatus.CREATED);
}
@GetMapping
public ResponseEntity<List<Trip>> findlltrip()
{
	List<Trip> list= tripRepository.findAll();	
return ResponseEntity.ok(list);
}

@GetMapping("/{id}")
public ResponseEntity<Trip> findTripById(@PathVariable("id")int id)
{
Trip trip=tripRepository.findById(id).orElse(null);
if(trip==null)
{
return ResponseEntity.notFound().build();	
}
return ResponseEntity.ok(trip);
}
@PutMapping
public ResponseEntity<Trip> UpdateTrip(@RequestBody Trip trip)
{
Trip trip1= tripRepository.save(trip);	
return ResponseEntity.ok(trip);

}


@DeleteMapping("/{id}")
public ResponseEntity<Trip> DeleteTrip(@PathVariable("id")int id)
{
	Trip trip=tripRepository.findById(id).orElse(null);
	if(trip==null)
	{
	return ResponseEntity.notFound().build();	
	}
	tripRepository.deleteById(id);
return ResponseEntity.noContent().build();
}
}
