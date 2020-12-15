package com.Banking_Application;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.Banking_Application.model.Customer;
import com.Banking_Application.repository.CustomerRepository;
import com.Banking_Application.service.BankingService;
import com.Banking_Application.service.BankingServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankingServiceImpl.class)
public class BankingServiceTest {

	@Autowired
	private BankingService bankingService;

	@MockBean
	private CustomerRepository customerRepository;

	@Test
	public void addCustomerTest() {

		Customer customerDetails = new Customer();
		customerDetails.setAccNumber(5229973);
		customerDetails.setAddress("123 Domain St");
		customerDetails.setCreateDateTime(new Date(2020, 02, 11));
		customerDetails.setCustId(101);
		customerDetails.setEmail("john@test.com");
		customerDetails.setFirstName("John");
		customerDetails.setLastName("Doe");
		customerDetails.setLoginId("C100");
		customerDetails.setPassword("Test");
		customerDetails.setPhoneNo("9783647537645");
		bankingService.addCustomer(customerDetails);

		// customerRepository.save(customerDetails);
		System.out.println("CODE : ****** " + bankingService.addCustomer(customerDetails).getStatusCode());
		Assert.assertEquals(HttpStatus.CREATED, bankingService.addCustomer(customerDetails).getStatusCode());
//		ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
//		verify(customerRepository.save(customerArgumentCaptor.capture());
//
//		Customer customerOutput = customerArgumentCaptor.getValue();
//
//		Assert.assertEquals(customerDetails.getAcc_number(), customerOutput.getAcc_number());

	}

	@Test
	public void updateCustomerTest() {

		Customer customerDetails = new Customer();
		customerDetails.setAccNumber(5229973);
		customerDetails.setAddress("123 Domain St");
		customerDetails.setCreateDateTime(new Date(2020, 02, 11));
		customerDetails.setCustId(100);
		customerDetails.setEmail("soumita@test.com");
		customerDetails.setFirstName("soumita");
		customerDetails.setLastName("Nag");
		customerDetails.setLoginId("C100");
		customerDetails.setPassword("Test");
		customerDetails.setPhoneNo("9783647537645");

		bankingService.updateCustomer(customerDetails, customerDetails.getCustId());

		Assert.assertEquals(HttpStatus.OK,
				bankingService.updateCustomer(customerDetails, customerDetails.getCustId()).getStatusCode());

	}
}
