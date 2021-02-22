package com.Banking_Application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking_Application.domain.CustomerDetails;
import com.Banking_Application.service.BankingServiceImpl;

@RestController
@RequestMapping("Banking_Application/customer")
public class CustomerController {

	@Autowired
	private BankingServiceImpl bankingService;

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "/all")
	public List<CustomerDetails> getAllCustomers() {

		return bankingService.findAll();
	}

	// check the id for login, give syso.. approuter.js L21-->login L46 -->auth.js
	// L16
	/*
	 * public ResponseEntity<Object> checkCredentials(@RequestBody CustomerDetails
	 * customerDetails){
	 * 
	 * }
	 */

	@PostMapping(path = "/add")
	public ResponseEntity<Object> addCustomer(@RequestBody CustomerDetails customer) {

		return bankingService.addCustomer(customer);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "/{customerNumber}")
	public CustomerDetails getCustomer(@PathVariable Long customerNumber) {

		return bankingService.findByCustomerNumber(customerNumber);
	}

	@PutMapping(path = "/{customerNumber}")
	public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDetails customerDetails,
			@PathVariable Long customerNumber) {

		return bankingService.updateCustomer(customerDetails, customerNumber);
	}

	@DeleteMapping(path = "/{customerNumber}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerNumber) {

		return bankingService.deleteCustomer(customerNumber);
	}

}
