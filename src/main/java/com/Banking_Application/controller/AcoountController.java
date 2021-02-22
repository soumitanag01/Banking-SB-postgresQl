package com.Banking_Application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking_Application.domain.AccountInformation;
import com.Banking_Application.domain.TransactionDetails;
import com.Banking_Application.domain.TransferDetails;
import com.Banking_Application.service.BankingServiceImpl;

@RestController
@RequestMapping(path = "Banking_Application/account",  produces = MediaType.APPLICATION_JSON_VALUE)//consumes = MediaType.APPLICATION_JSON_VALUE,
public class AcoountController {

	@Autowired
	private BankingServiceImpl bankingService;

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "/{accountNumber}")
	public ResponseEntity<Object> getByAccountNumber(@PathVariable Long accountNumber) {
		System.out.println("within getByAccountNumber: "+ accountNumber);
		return bankingService.findByAccountNumber(accountNumber);
	}

	@PostMapping(path = "/add/{accountNumber}")
	public ResponseEntity<Object> addnewAccount(@RequestBody AccountInformation accountInformation,
			@PathVariable("accountNumber") Long customerNumber) {
		return bankingService.addNewAccount(accountInformation, customerNumber);
	}

	@PutMapping(path = "/transfer/{customerNumber}")
	public ResponseEntity<Object> getTransferDetails(@RequestBody TransferDetails transferDetails,
			@PathVariable Long customerNumber) {
		return bankingService.transferDetails(transferDetails, customerNumber);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "/transactions/{accountNumber}")
	public List<TransactionDetails> getTransactionByAccountNumber(@PathVariable Long accountNumber) {
		return bankingService.findTransactionsByAccountNumber(accountNumber);
	}
}
