package com.Banking_Application.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Banking_Application.model.Account;
import com.Banking_Application.model.Customer;
import com.Banking_Application.model.Transaction;
import com.Banking_Application.repository.AccountRepository;
import com.Banking_Application.repository.CustomerRepository;

@Service
public class BankingServiceImpl implements BankingService {

	// @Autowired
	private Customer customer;

	// @Autowired
	private Account account;

	// @Autowired
	private Transaction transaction;

	// @Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	public BankingServiceImpl(CustomerRepository repository) {
		this.customerRepository = repository;
	}

	/**
	 * Method for creating a new customer
	 */
	@Override
	public ResponseEntity<Object> addCustomer(Customer customer) {
		// TODO Auto-generated method stub

		System.out.println(customerRepository.findAll());
		customer.setCreateDateTime(new Date());
		try {
			customerRepository.save(customer);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("New customer has been created!");
	}

	@Override
	public ResponseEntity<Object> updateCustomer(Customer customerDetails, Integer custId) {
		// TODO Auto-generated method stub

		System.out.println(customerRepository.findAll().toString());
		customer = findbyCustomerId(custId);
		if (customer != null) {
			customer.setAccNumber(customerDetails.getAccNumber());
			customer.setAddress(customerDetails.getAddress());
			customer.setCreateDateTime(customerDetails.getCreateDateTime());
			customer.setCustId(custId);
			customer.setEmail(customerDetails.getEmail());
			customer.setFirstName(customerDetails.getFirstName());
			customer.setLastName(customerDetails.getLastName());
			customer.setLoginId(customerDetails.getLoginId());
			customer.setPassword(customerDetails.getPassword());
			customer.setPhoneNo(customerDetails.getPhoneNo());

			customerRepository.save(customer);
			return ResponseEntity.status(HttpStatus.OK).body("Customer has been updated");

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Customer id : " + custId + " has not been found!!");

		}

	}

	@Override
	public ResponseEntity<Object> deleteCustomer(Integer custId) {
		// TODO Auto-generated method stub
		customer = findbyCustomerId(custId);
		if (customer != null) {
			customerRepository.delete(customer);
			return ResponseEntity.status(HttpStatus.OK).body("Customer has been deleted!");

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Customer id : " + custId + " has not been found!!");

		}
	}

	@Override
	public ResponseEntity<Object> addNewAccount(Account accountInformation, Integer custId) {
		// TODO Auto-generated method stub
		customer = findbyCustomerId(custId);
		if (customer != null)
			customerRepository.save(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body("New customer has been created!");

	}

	public Account findByAccountNumber(int accNumber) {
		// TODO Auto-generated method stub
		Optional<Account> accountEntity = accountRepository.findByAccNumber(accNumber);
		if (accountEntity.isPresent())
			return accountEntity.get();
		return null;
	}

	public Customer findbyCustomerId(Integer custId) {
		Optional<Customer> customerEntity = customerRepository.findByCustId(custId);

		if (customerEntity.isPresent())
			return customerEntity.get();
		return null;
	}

}
