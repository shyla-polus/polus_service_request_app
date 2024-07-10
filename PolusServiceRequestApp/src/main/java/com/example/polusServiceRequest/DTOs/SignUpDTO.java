package com.example.polusServiceRequest.DTOs;

import lombok.Data;

@Data
public class SignUpDTO {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	private String country;
	private String phoneNo;
	private String address;

}