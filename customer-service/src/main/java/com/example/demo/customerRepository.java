package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Customer;

public interface customerRepository  extends JpaRepository<Customer, Integer>{

}
