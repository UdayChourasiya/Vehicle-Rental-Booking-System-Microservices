package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.example.demo.dao.VehicleRepository;
import com.example.demo.model.Vehicle;

@RequestMapping("/vehicles")
@RestController
public class VehicleController {
private VehicleRepository vehicleRepository;

public VehicleController(VehicleRepository vehicleRepository) {
	super();
	this.vehicleRepository = vehicleRepository;
}

@PostMapping
public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle)
{
Vehicle vehicle1= vehicleRepository.save(vehicle);
return new ResponseEntity<>(vehicle1,HttpStatus.CREATED);
}

@GetMapping
public ResponseEntity<List<Vehicle>> findallVehicle()
{
	List<Vehicle> list= vehicleRepository.findAll();
return ResponseEntity.ok(list);
}

@GetMapping("/{id}")
public ResponseEntity<Vehicle> findvehicleById(@PathVariable("id") int id)
{
Vehicle vehicle= vehicleRepository.findById(id).orElse(null);	
if(vehicle==null)
{
return ResponseEntity.notFound().build();
}
return ResponseEntity.ok(vehicle);
}

@PutMapping
public ResponseEntity<Vehicle> UpdateVehicle(@RequestBody Vehicle vehicle)
{
Vehicle vehicle1= vehicleRepository.save(vehicle);
return ResponseEntity.ok(vehicle1);

}
@DeleteMapping("/{id}")
public ResponseEntity<Vehicle> deleteVehicleById(@PathVariable("id") int id)
{
	Vehicle vehicle= vehicleRepository.findById(id).orElse(null);	
	if(vehicle==null)
	{
	return ResponseEntity.notFound().build();
	}

	vehicleRepository.deleteById(id);
return ResponseEntity.noContent().build();	
}
		
}
