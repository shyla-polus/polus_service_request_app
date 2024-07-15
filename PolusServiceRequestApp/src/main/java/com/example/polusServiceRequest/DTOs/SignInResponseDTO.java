package com.example.polusServiceRequest.DTOs;

import java.util.Date;
import java.util.List;

import com.example.polusServiceRequest.models.CountryEntity;

import lombok.Data;


public class SignInResponseDTO {

	private Long userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private CountryEntity country;
	private String phoneNo;
	private String address;
	private Date createdDate;
	private List<RoleDTO> roles;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public CountryEntity getCountry() {
		return country;
	}
	public void setCountry(CountryEntity countryEntity) {
		this.country = countryEntity;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public List<RoleDTO> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}
	
	
	
	

}