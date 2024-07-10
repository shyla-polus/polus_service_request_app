package com.example.polusServiceRequest.DTOs;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SignInResponseDTO {

	private Long userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String country;
	private String phoneNo;
	private String address;
	private Date createdDate;
	private List<RoleDTO> roles;

}