package com.example.polusServiceRequest.DTOs;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.example.polusServiceRequest.models.CountryEntity;

import lombok.Data;

@Data
public class SignInResponseDTO {

	private Long userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private CountryEntity country;
	private String phoneNo;
	private String address;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private List<RoleDTO> roles;

}