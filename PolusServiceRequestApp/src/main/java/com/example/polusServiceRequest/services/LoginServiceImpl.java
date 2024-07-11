package com.example.polusServiceRequest.services;

import com.example.polusServiceRequest.DTOs.CountryDTO;
import com.example.polusServiceRequest.DTOs.RoleDTO;
import com.example.polusServiceRequest.DTOs.SignInResponseDTO;
import com.example.polusServiceRequest.DTOs.SignUpDTO;
import com.example.polusServiceRequest.constants.Constants;
import com.example.polusServiceRequest.models.CountryEntity;
import com.example.polusServiceRequest.models.RoleEntity;
import com.example.polusServiceRequest.models.UserEntity;
import com.example.polusServiceRequest.models.UserRoleEntity;
import com.example.polusServiceRequest.repositories.UserRepository;
import com.example.polusServiceRequest.repositories.CountryRepository;
import com.example.polusServiceRequest.repositories.RoleRepository;
import com.example.polusServiceRequest.repositories.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository loginRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
    private CountryRepository countryRepository;  // New repository

	@Override
	public SignInResponseDTO signIn(String username, String password) {
		try {
			UserEntity user = loginRepository.findByUsernameAndPassword(username, password);
			if (user != null) {

				SignInResponseDTO responseDTO = new SignInResponseDTO();
				responseDTO.setUserId(user.getUserId());
				responseDTO.setFirstName(user.getFirstName());
				responseDTO.setLastName(user.getLastName());
				responseDTO.setUserName(user.getUsername());
				responseDTO.setEmail(user.getEmail());
				responseDTO.setCountry(user.getCountry());
				responseDTO.setPhoneNo(user.getPhoneNo());
				responseDTO.setAddress(user.getAddress());
				responseDTO.setCreatedDate(user.getCreatedDate());

				List<RoleDTO> roleDTOs = user.getRoles().stream().map(userRole -> {
					RoleDTO roleDTO = new RoleDTO();
					roleDTO.setRoleId(userRole.getRole().getRoleId());
					roleDTO.setRoleName(userRole.getRole().getRoleName());
					roleDTO.setRoleDescription(userRole.getRole().getRoleDescription());
					return roleDTO;
				}).collect(Collectors.toList());
				responseDTO.setRoles(roleDTOs);
				return responseDTO;
			} else {
				throw new RuntimeException("Authentication failed");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error during sign-in: " + e.getMessage(), e);
		}
	}

	@Override
	public boolean signUp(SignUpDTO signUpDTO) {

		try {
			// Check if the username already exists
			if (loginRepository.findByUsername(signUpDTO.getUserName()) != null) {
				throw new RuntimeException("Username already exists");
			}

			UserEntity newUser = new UserEntity();
			newUser.setFirstName(signUpDTO.getFirstName());
			newUser.setLastName(signUpDTO.getLastName());
			newUser.setUsername(signUpDTO.getUserName());
			newUser.setPassword(signUpDTO.getPassword());
			newUser.setEmail(signUpDTO.getEmail());
			newUser.setCountry(signUpDTO.getCountry());
			newUser.setPhoneNo(signUpDTO.getPhoneNo());
			newUser.setAddress(signUpDTO.getAddress());
			newUser.setCreatedDate(new Date());
			UserEntity savedUser = loginRepository.save(newUser);

			RoleEntity role = roleRepository.findByRoleName(Constants.DEFAULT_ROLE);
			if (role == null) {
				throw new RuntimeException("Role not found");
			}

			UserRoleEntity userRole = new UserRoleEntity();
			userRole.setUser(savedUser);
			userRole.setRole(role);
			userRoleRepository.save(userRole);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Error during sign-up: " + e.getMessage(), e);
		}
	}
	
	 @Override
	    public List<CountryDTO> getAllCountries() {
	        return countryRepository.findAll().stream()
	                .map(country -> new CountryDTO(
	                    country.getCountryCode(),
	                    country.getCountryName(),
	                    country.getCurrencyCode(),
	                    country.getUpdateTimestamp(),
	                    country.getUpdateUser(),
	                    country.getCountryCodeIso2()
	                ))
	                .collect(Collectors.toList());
	    }

}