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
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer custId;

	@Column(name = "firstName", nullable = false)
	private String firstName;
	@Column(name = "lastName", nullable = false)
	private String lastName;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "phoneNo", nullable = false)
	private String phoneNo;
	@Column(name = "loginId", nullable = false)
	private String loginId;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "acc_number", nullable = false)
	private int accNumber;
	@Column(name = "create_DateTime", nullable = false)
	private Date createDateTime;

}
