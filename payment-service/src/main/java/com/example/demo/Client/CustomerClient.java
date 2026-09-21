package com.example.demo.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.CustomerDto;

@FeignClient(name="customer-service")
public interface CustomerClient {
	 @GetMapping("/customers/{id}")
	public CustomerDto findcustomerById(@PathVariable("id") int id);

}
