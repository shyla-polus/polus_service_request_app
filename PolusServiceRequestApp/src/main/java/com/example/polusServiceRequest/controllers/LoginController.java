package com.example.polusServiceRequest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.polusServiceRequest.DTOs.AdminDTO;
import com.example.polusServiceRequest.DTOs.CountryDTO;
import com.example.polusServiceRequest.DTOs.SignInDTO;
import com.example.polusServiceRequest.DTOs.SignInResponseDTO;
import com.example.polusServiceRequest.DTOs.SignUpDTO;
import com.example.polusServiceRequest.models.CountryEntity;
import com.example.polusServiceRequest.models.PersonEntity;
import com.example.polusServiceRequest.services.LoginService;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<Object> signIn(@RequestBody SignInDTO signInDTO) {
		try {
			SignInResponseDTO responseDTO = loginService.signIn(signInDTO.getUserName(), signInDTO.getPassword());
			return ResponseEntity.ok().body(responseDTO);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during sign-in.");
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<Object> signUp(@RequestBody SignUpDTO signUpDTO) {
		Map<String, String> response = new HashMap<>();
		try {
			boolean registered = loginService.signUp(signUpDTO);
			if (registered) {
				response.put("message", "User registration successful");
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} else {
				response.put("message", "User registration failed");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during sign-up.");
		}
	}

	@GetMapping("/countries")
	public ResponseEntity<List<CountryDTO>> getCountries() {
		try {
			List<CountryDTO> countries = loginService.getAllCountries();
			return ResponseEntity.ok().body(countries);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
