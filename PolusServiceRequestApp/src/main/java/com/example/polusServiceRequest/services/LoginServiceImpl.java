package com.example.polusServiceRequest.services;

import com.example.polusServiceRequest.DTOs.AdminDTO;
import com.example.polusServiceRequest.DTOs.CountryDTO;
import com.example.polusServiceRequest.DTOs.RoleDTO;
import com.example.polusServiceRequest.DTOs.SignInResponseDTO;
import com.example.polusServiceRequest.DTOs.SignUpDTO;
import com.example.polusServiceRequest.constants.RoleNamesConstants;
import com.example.polusServiceRequest.models.CountryEntity;
import com.example.polusServiceRequest.models.PersonEntity;
import com.example.polusServiceRequest.models.PersonRoleEntity;
import com.example.polusServiceRequest.models.RoleEntity;
import com.example.polusServiceRequest.repositories.PersonRepository;
import com.example.polusServiceRequest.repositories.RoleRepository;
import com.example.polusServiceRequest.repositories.PersonRoleRepository;
import com.example.polusServiceRequest.repositories.CountryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PersonRoleRepository personRoleRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Override
	public SignInResponseDTO signIn(String username, String password) {
		try {
			PersonEntity person = personRepository.findByUserNameAndPassword(username, password);
			if (person != null) {
				SignInResponseDTO responseDTO = new SignInResponseDTO();
				responseDTO.setUserId(person.getPersonId());
				responseDTO.setFirstName(person.getFirstName());
				responseDTO.setLastName(person.getLastName());
				responseDTO.setUserName(person.getUserName());
				responseDTO.setEmail(person.getEmail());
				responseDTO.setCountry(person.getCountry());
				responseDTO.setPhoneNo(person.getPhoneNumber());
				responseDTO.setAddress(person.getAddress());
				responseDTO.setCreatedDate(person.getCreateTimestamp());
				responseDTO.setUpdatedDate(person.getUpdateTimestamp());

				List<RoleDTO> roleDTOs = new ArrayList<>();
				for (PersonRoleEntity personRole : person.getRoles()) {
					RoleDTO roleDTO = new RoleDTO();
					roleDTO.setRoleId(personRole.getRole().getRoleId());
					roleDTO.setRoleName(personRole.getRole().getRoleName());
					roleDTO.setRoleDescription(personRole.getRole().getRoleDescription());
					roleDTOs.add(roleDTO);
				}
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
			if (personRepository.findByUserName(signUpDTO.getUserName()) != null) {
				throw new RuntimeException("Username already exists");
			}

			PersonEntity newPerson = new PersonEntity();
			Optional<CountryEntity> country = countryRepository.findById(signUpDTO.getCountry());
			newPerson.setFirstName(signUpDTO.getFirstName());
			newPerson.setLastName(signUpDTO.getLastName());
			newPerson.setUserName(signUpDTO.getUserName());
			newPerson.setPassword(signUpDTO.getPassword());
			newPerson.setEmail(signUpDTO.getEmail());
			newPerson.setCountry(country.get());
			newPerson.setPhoneNumber(signUpDTO.getPhoneNo());
			newPerson.setAddress(signUpDTO.getAddress());
			newPerson.setCreateTimestamp(Timestamp.from(Instant.now()));
			newPerson.setUpdateTimestamp(Timestamp.from(Instant.now()));
			PersonEntity savedPerson = personRepository.save(newPerson);

			RoleEntity role = roleRepository.findByRoleName(RoleNamesConstants.DEFAULT_ROLE);
			if (role == null) {
				throw new RuntimeException("Role not found");
			}

			PersonRoleEntity personRole = new PersonRoleEntity();
			personRole.setPerson(savedPerson);
			personRole.setRole(role);
			personRoleRepository.save(personRole);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Error during sign-up: " + e.getMessage(), e);
		}
	}

	@Override
	public List<CountryDTO> getAllCountries() {
		List<CountryEntity> countries = countryRepository.findAll();
		List<CountryDTO> countryDTOs = new ArrayList<>();

		for (CountryEntity country : countries) {
			CountryDTO dto = new CountryDTO();
			dto.setCountryCode(country.getCountryCode());
			dto.setCountryName(country.getCountryName());
			dto.setCurrencyCode(country.getCurrencyCode());
			dto.setUpdateTimestamp(country.getUpdateTimestamp());
			dto.setUpdateUser(country.getUpdateUser());
			dto.setCountryCodeIso2(country.getCountryCodeIso2());
			countryDTOs.add(dto);
		}

		return countryDTOs;
	}

	@Override
	public List<AdminDTO> getAllApplicationAdministrators() {
		List<PersonEntity> administrators = personRepository.findAllApplicationAdministrators();
		List<AdminDTO> administratorDTOs = new ArrayList<>();
		for (PersonEntity person : administrators) {
			AdminDTO dto = new AdminDTO();
			dto.setPersonId(person.getPersonId());
			dto.setFirstName(person.getFirstName());
			dto.setLastName(person.getLastName());
			administratorDTOs.add(dto);
		}
		return administratorDTOs;
	}
}
