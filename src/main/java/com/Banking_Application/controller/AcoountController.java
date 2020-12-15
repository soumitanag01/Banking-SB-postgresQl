package com.Banking_Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking_Application.model.Account;
import com.Banking_Application.model.Customer;
import com.Banking_Application.service.BankingServiceImpl;

@RestController
@RequestMapping("Banking_Applicatin/account")
public class AcoountController {

	@Autowired
	private BankingServiceImpl bankingServiceImpl;

	@PostMapping("/addCustomer")
	public ResponseEntity<Object> addCustomer(@RequestBody Customer customerDetails) {

		return bankingServiceImpl.addCustomer(customerDetails);

	}

	@PutMapping("/updateCustomer")
	public ResponseEntity<Object> updateCustomerByCustId(@RequestBody Customer customerDetails,
			@PathVariable Integer cust_id) {
		return bankingServiceImpl.updateCustomer(customerDetails, cust_id);
	}

	@DeleteMapping("/removeCustomer")
	public ResponseEntity<Object> deleteCustomerByCustId(@PathVariable Integer cust_id) {

		return bankingServiceImpl.deleteCustomer(cust_id);
	}

	@PutMapping("/addAccount")
	public ResponseEntity<Object> addNewAccount(@RequestBody Account accountInformation, @PathVariable Integer cust_id) {

		return bankingServiceImpl.addNewAccount(accountInformation, cust_id);
	}

}
