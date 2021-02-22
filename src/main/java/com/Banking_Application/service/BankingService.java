package com.Banking_Application.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.Banking_Application.domain.AccountInformation;
import com.Banking_Application.domain.CustomerDetails;
import com.Banking_Application.domain.TransactionDetails;
import com.Banking_Application.domain.TransferDetails;

public interface BankingService {

	public List<CustomerDetails> findAll();

	public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails);

	public CustomerDetails findByCustomerNumber(Long customerNumber);

	public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber);

	public ResponseEntity<Object> deleteCustomer(Long customerNumber);

	public ResponseEntity<Object> findByAccountNumber(Long accountNumber);

	// public ResponseEntity<Object> findByEmail(String email);

	public ResponseEntity<Object> addNewAccount(AccountInformation accountInformation, Long customerNumber);

	public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber);

	public List<TransactionDetails> findTransactionsByAccountNumber(Long accountNumber);

}
