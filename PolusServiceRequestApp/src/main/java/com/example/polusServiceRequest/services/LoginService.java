package com.example.polusServiceRequest.services;

import java.util.List;

import com.example.polusServiceRequest.DTOs.AdminDTO;
import com.example.polusServiceRequest.DTOs.CountryDTO;
import com.example.polusServiceRequest.DTOs.SignInResponseDTO;
import com.example.polusServiceRequest.DTOs.SignUpDTO;

public interface LoginService {

	SignInResponseDTO signIn(String username, String password);

	boolean signUp(SignUpDTO signUpDTO);
	
	List<CountryDTO> getAllCountries();
	
	List<AdminDTO> getAllApplicationAdministrators();

}