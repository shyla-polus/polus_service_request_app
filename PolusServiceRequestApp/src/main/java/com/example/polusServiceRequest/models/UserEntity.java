package com.example.polusServiceRequest.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid")
	private Long userId;

	@Column(name = "firstname", nullable = false)
	private String firstName;

	@Column(name = "lastname")
	private String lastName;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@JsonIgnore
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "country", referencedColumnName = "COUNTRY_CODE")
//	private CountryEntity country;
	 
	@ManyToOne
	@JoinColumn(name = "COUNTRY_CODE",referencedColumnName = "COUNTRY_CODE")
	private CountryEntity countryCode;
//	
//	@Column(name = "country")
//	private String country; 
	
	@Column(name = "phoneno")
	private String phoneNo;

	@Column(name = "address")
	private String address;

	@LastModifiedDate
	@Column(name = "created_date", nullable = false, updatable = false)
	private Date createdDate;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<UserRoleEntity> roles;

}
