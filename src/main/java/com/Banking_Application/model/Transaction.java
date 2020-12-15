package com.Banking_Application.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long transactionId;

	@Column(name = "acc_number", nullable = false)
	private long accNumber;
	@Column(name = "transaction_date", nullable = false)
	private Date transactionDate;
	@Column(name = "amount", nullable = false)
	private double amount;
	@Column(name = "type", nullable = false)
	private String type;

}
