package com.example.polusServiceRequest.DTOs;

import com.example.polusServiceRequest.models.CountryEntity;

import lombok.Data;

@Data
public class SignUpDTO {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	private CountryEntity country;//changed
	private String phoneNo;
	private String address;

}