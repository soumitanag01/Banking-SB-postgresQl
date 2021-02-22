package com.Banking_Application.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferDetails {

	private Long fromAccountNumber;
	
	private Long toAccountNumber;
	
	private Double transferAmount;
}