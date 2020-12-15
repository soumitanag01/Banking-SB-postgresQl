package com.Banking_Application.service;

import org.springframework.http.ResponseEntity;

import com.Banking_Application.model.Account;
import com.Banking_Application.model.Customer;

public interface BankingService {

	public ResponseEntity<Object> addCustomer(Customer customerDetails);

	public ResponseEntity<Object> updateCustomer(Customer customerDetails, Integer cust_id);

	public ResponseEntity<Object> deleteCustomer(Integer cust_id);

	public ResponseEntity<Object> addNewAccount(Account accountInformation, Integer cust_id);

}
