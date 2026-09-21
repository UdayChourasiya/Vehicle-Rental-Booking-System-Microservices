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

import com.example.demo.dao.customerRepository;
import com.example.demo.model.Customer;
@RequestMapping("/customers")
@RestController
public class CustomerController {
private customerRepository customerRepository;

public CustomerController(customerRepository customerRepository) {
	this.customerRepository = customerRepository;
}

@PostMapping
public ResponseEntity<Customer> addcustomer(@RequestBody Customer customer )
{
Customer customer1=customerRepository.save(customer);
return new ResponseEntity<Customer>(customer1,HttpStatus.CREATED);
}
@GetMapping
public ResponseEntity< List<Customer>> findaCustomersllCustomers()
{
	List<Customer> list=customerRepository.findAll();	
return ResponseEntity.ok(list);
}

@GetMapping("/{id}")
public ResponseEntity<Customer> findcustomerById(@PathVariable("id") int id)
{
	
Customer customer= customerRepository.findById(id).orElse(null);
if(customer==null)
{
return ResponseEntity.notFound().build();	
}
return ResponseEntity.ok(customer);
}


@PutMapping
public ResponseEntity<Customer> UpdateCustomer(@RequestBody Customer customer)
{
	Customer customer1=customerRepository.save(customer);
return ResponseEntity.ok(customer1);
}


@DeleteMapping("/{id}")
public 	ResponseEntity<Customer> deleteCustomer(@PathVariable("id")int id)
{
	Customer customer= customerRepository.findById(id).orElse(null);
	if(customer==null)
	{
	return ResponseEntity.notFound().build();	
	}

	customerRepository.deleteById(id);
return ResponseEntity.noContent().build();	
}

}
