package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Customer;

public interface customerRepository extends JpaRepository<Customer, Integer>{

}
