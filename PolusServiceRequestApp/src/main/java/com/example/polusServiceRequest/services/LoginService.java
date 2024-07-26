package com.example.polusServiceRequest.services;

import java.util.List;

import com.example.polusServiceRequest.DTOs.SignInResponseDTO;
import com.example.polusServiceRequest.DTOs.SignUpDTO;
import com.example.polusServiceRequest.models.CountryEntity;

public interface LoginService {

	SignInResponseDTO signIn(String username, String password);

	boolean signUp(SignUpDTO signUpDTO);

	List<CountryEntity> getAllCountries();

	

}