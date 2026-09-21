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

import com.example.demo.dao.DriverRepository;
import com.example.demo.model.Driver;

import jakarta.ws.rs.Path;
@RequestMapping("/drivers")
@RestController
public class DriverController {
private DriverRepository driverRepository;

public DriverController(DriverRepository driverRepository) {
	super();
	this.driverRepository = driverRepository;
}

@PostMapping
public ResponseEntity<Driver> addDriver(@RequestBody Driver driver)
{
	Driver driver1=driverRepository.save(driver); 	
return new ResponseEntity<>(driver1,HttpStatus.CREATED);
}

@GetMapping
public ResponseEntity<List<Driver>> fidAllDriver()
{
	List<Driver> list =driverRepository.findAll(); 	
return ResponseEntity.ok(list);
}


@GetMapping("/{id}")
public ResponseEntity<Driver> findDriverById(@PathVariable("id") int id)
{
	
Driver driver=driverRepository.findById(id).orElse(null);
if(driver==null)
{
return ResponseEntity.notFound().build();	
}
	return ResponseEntity.ok(driver);	
}


@PutMapping
public ResponseEntity<Driver> UpdateDriver(@RequestBody Driver driver)
{
Driver driver1=driverRepository.save(driver);	
return ResponseEntity.ok(driver1);	
}


@DeleteMapping("/{id}")
public ResponseEntity<Driver> DeleteDriver(@PathVariable("id")int id)
{
	
Driver driver=driverRepository.findById(id).orElse(null);
if(driver==null)
{
return ResponseEntity.notFound().build();	
}
driverRepository.deleteById(id);		
return ResponseEntity.noContent().build();	
}
}
