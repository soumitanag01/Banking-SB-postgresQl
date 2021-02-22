package com.Banking_Application.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Banking_Application.domain.AccountInformation;
import com.Banking_Application.domain.CustomerDetails;
import com.Banking_Application.domain.TransactionDetails;
import com.Banking_Application.domain.TransferDetails;
import com.Banking_Application.model.Account;
import com.Banking_Application.model.Address;
import com.Banking_Application.model.Contact;
import com.Banking_Application.model.Customer;
import com.Banking_Application.model.CustomerAccountXRef;
import com.Banking_Application.model.Transaction;
import com.Banking_Application.repository.AccountRepository;
import com.Banking_Application.repository.CustomerAccountXRefRepository;
import com.Banking_Application.repository.CustomerRepository;
import com.Banking_Application.repository.TransactionRepository;

//https://github.com/sbathina/BankApp/blob/master/src/main/java/com/coding/exercise/bankapp/service/BankingServiceImpl.java
@Service
public class BankingServiceImpl implements BankingService {

	/*
	 * @Autowired private Customer customer;
	 * 
	 * @Autowired private Account account;
	 * 
	 * @Autowired private Transaction transaction;
	 */

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerAccountXRefRepository custAccXRefRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private BankingServiceHelper bankingServiceHelper;

	public BankingServiceImpl(CustomerRepository repository) {
		this.customerRepository = repository;
	}

	@Override
	public List<CustomerDetails> findAll() {
		List<CustomerDetails> allCustomerDetails = new ArrayList<CustomerDetails>();

		Iterable<Customer> customerList = customerRepository.findAll();

		customerList.forEach(customer -> {
			allCustomerDetails.add(bankingServiceHelper.convertToCustomerDomain(customer));
		});
		return allCustomerDetails;
	}

	/**
	 * Add new customer/CREATE
	 */
	@Override
	public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails) {
		// TODO Auto-generated method stub
		Customer customer = bankingServiceHelper.convertToCustomerEntity(customerDetails);
		customer.setCreateDateTime(new Date());
		customerRepository.save(customer);

		return ResponseEntity.status(HttpStatus.CREATED).body("New CUstomer has been created");
	}

	/**
	 * Find a customer /READ
	 */
	@Override
	public CustomerDetails findByCustomerNumber(Long customerNumber) {
		Optional<Customer> customerEntity = customerRepository.findByCustomerNumber(customerNumber);
		if (customerEntity.isPresent())
			return bankingServiceHelper.convertToCustomerDomain(customerEntity.get());

		return null;

	}

	/**
	 * Update Customer/UPDATE
	 */
	@Override
	public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber) {
		Optional<Customer> customerEntityFromRepo = customerRepository.findByCustomerNumber(customerNumber);
		Customer customerEnt = bankingServiceHelper.convertToCustomerEntity(customerDetails);

		if (customerEntityFromRepo.isPresent()) {
			Customer managedCustomerEntity = customerEntityFromRepo.get();
			// check for contact details
			if (Optional.ofNullable(customerEnt.getContactDetails()).isPresent()) {

				Contact managedContact = managedCustomerEntity.getContactDetails();
				if (managedContact != null) {
					managedContact.setEmailId(customerEnt.getContactDetails().getEmailId());
					managedContact.setHomePhone(customerEnt.getContactDetails().getHomePhone());
					managedContact.setWorkPhone(customerEnt.getContactDetails().getWorkPhone());

				} else {
					managedCustomerEntity.setContactDetails(customerEnt.getContactDetails());
				}

			}
			// check for address details
			if (Optional.ofNullable(customerEnt.getCustomerAddress()).isPresent()) {
				Address managedAddress = managedCustomerEntity.getCustomerAddress();
				if (managedAddress != null) {
					managedAddress.setAddress1(customerEnt.getCustomerAddress().getAddress1());
					managedAddress.setAddress2(customerEnt.getCustomerAddress().getAddress2());
					managedAddress.setCity(customerEnt.getCustomerAddress().getCity());
					managedAddress.setState(customerEnt.getCustomerAddress().getState());
					managedAddress.setZip(customerEnt.getCustomerAddress().getZip());
					managedAddress.setCountry(customerEnt.getCustomerAddress().getCountry());
				} else {
					managedCustomerEntity.setCustomerAddress(customerEnt.getCustomerAddress());
				}
			}
			managedCustomerEntity.setUpdateDateTime(new Date());
			managedCustomerEntity.setStatus(customerEnt.getStatus());
			managedCustomerEntity.setFirstName(customerEnt.getFirstName());
			managedCustomerEntity.setMiddleName(customerEnt.getMiddleName());
			managedCustomerEntity.setLastName(customerEnt.getLastName());
			managedCustomerEntity.setUpdateDateTime(new Date());

			customerRepository.save(managedCustomerEntity);

			return ResponseEntity.status(HttpStatus.OK).body("Customer Updated");

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer " + customerNumber + " Not found");

		}

	}

	/**
	 * DELETE customer
	 */
	@Override
	public ResponseEntity<Object> deleteCustomer(Long customerNumber) {
		Optional<Customer> customerEntityFromRepo = customerRepository.findByCustomerNumber(customerNumber);
		if (customerEntityFromRepo.isPresent()) {
			Customer managedCustomerEntity = customerEntityFromRepo.get();
			customerRepository.delete(managedCustomerEntity);
			return ResponseEntity.status(HttpStatus.OK).body("Customer Deleted");

		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer " + customerNumber + " Not found");

		}
	}

	@Override
	public ResponseEntity<Object> findByAccountNumber(Long accountNumber) {

		Optional<Account> accountEntityFromRepo = accountRepository.findByAccountNumber(accountNumber);
		if (accountEntityFromRepo.isPresent()) {
			AccountInformation information = bankingServiceHelper.convertToAccountDomain(accountEntityFromRepo.get());
			return ResponseEntity.status(HttpStatus.OK).body(information);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Number " + accountNumber + " Not found");

		}
	}

	@Override
	public ResponseEntity<Object> addNewAccount(AccountInformation accountInformation, Long customerNumber) {
		Optional<Customer> customerEntityFromRepo = customerRepository.findByCustomerNumber(customerNumber);
		if (customerEntityFromRepo.isPresent()) {
			Account account = bankingServiceHelper.convertToAccountEntity(accountInformation);
			accountRepository.save(account);

			custAccXRefRepository.save(CustomerAccountXRef.builder()
					.accountNumber(accountInformation.getAccountNumber()).customerNumber(customerNumber).build());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("New account created");

	}

	/**
	 * Transfer funds from one account to another for a specific customer
	 */
	@Override
	public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber) {
		Account fromAccountEntity = null;
		Account toAccountEntity = null;
		List<Account> accountEntities = new ArrayList<>();

		Optional<Customer> customerEntity = customerRepository.findByCustomerNumber(customerNumber);
		if (customerEntity.isPresent()) {
			// from acc
			Optional<Account> fromAccountEntityOptional = accountRepository
					.findByAccountNumber(transferDetails.getFromAccountNumber());
			if (fromAccountEntityOptional.isPresent()) {
				fromAccountEntity = fromAccountEntityOptional.get();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("From Account Number " + transferDetails.getFromAccountNumber() + " Not found");

			}
			// to acc
			Optional<Account> toAccountEntityOptional = accountRepository
					.findByAccountNumber(customerNumber);//(transferDetails.getFromAccountNumber());
			if (toAccountEntityOptional.isPresent()) {
				toAccountEntity = toAccountEntityOptional.get();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("To Account Number " + transferDetails.getFromAccountNumber() + " Not found");
			}

			// not enough funds
			if (fromAccountEntity.getAccountBalance() < toAccountEntity.getAccountBalance()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds");
			} else {
				synchronized (this) {// for transaction things
					// update from acc
					fromAccountEntity.setAccountBalance(
							fromAccountEntity.getAccountBalance() - transferDetails.getTransferAmount());
					System.out.println("updated from acc: "+fromAccountEntity.getAccountBalance() );

					fromAccountEntity.setUpdateDateTime(new Date());
					accountEntities.add(fromAccountEntity);
					// update to acc
					toAccountEntity.setAccountBalance(
							toAccountEntity.getAccountBalance() + transferDetails.getTransferAmount());
					toAccountEntity.setUpdateDateTime(new Date());
					System.out.println("toacc old bal : "+toAccountEntity.getAccountBalance() );

					System.out.println("tranffered amount : "+transferDetails.getTransferAmount() );

					System.out.println("updated to acc: "+toAccountEntity.getAccountBalance() );
					accountEntities.add(toAccountEntity);
					accountRepository.saveAll(accountEntities);
					// transaction from acc
					Transaction fromTransaction = bankingServiceHelper.createTransaction(transferDetails,
							fromAccountEntity.getAccountNumber(), "DEBIT");
					transactionRepository.save(fromTransaction);
					// transaction to acc
					Transaction toTransaction = bankingServiceHelper.createTransaction(transferDetails,
							toAccountEntity.getAccountNumber(), "CREDIT");
					transactionRepository.save(toTransaction);
				}
				return ResponseEntity.status(HttpStatus.OK)
						.body("Success: Amount transferred for Customer Number " + customerNumber);

			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Customer Number " + customerNumber + " not found.");

		}

	}

	/**
	 * Get all transactions for a specific account
	 */
	@Override
	public List<TransactionDetails> findTransactionsByAccountNumber(Long accountNumber) {
		List<TransactionDetails> transactionDetails = new ArrayList<>();
		Optional<Account> accountEntityOptional = accountRepository.findByAccountNumber(accountNumber);
		if (accountEntityOptional.isPresent()) {
			Optional<List<Transaction>> transactionEntitiesOpt = transactionRepository
					.findByAccountNumber(accountNumber);
			if (transactionEntitiesOpt.isPresent()) {
				transactionEntitiesOpt.get().forEach(transaction -> {
					transactionDetails.add(bankingServiceHelper.convertToTransactionDomain(transaction));
				});
			}
		}
		return transactionDetails;
	}


}
