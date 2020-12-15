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
@Table(name = "accounts")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accNumber;

	@Column(name = "type", nullable = false)
	private String type;
	@Column(name = "holder_name", nullable = false)
	private String holderFirstName;// first name
	@Column(name = "date_opened", nullable = false)
	private Date dateOpened;
	@Column(name = "cust_id", nullable = false)
	private String custId;
	@Column(name = "balance", nullable = false)
	private double balance;
	@Column(name = "password", nullable = false)
	private String password;

	
}
