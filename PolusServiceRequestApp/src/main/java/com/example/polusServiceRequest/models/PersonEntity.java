package com.example.polusServiceRequest.models;

import java.sql.Timestamp;
import java.util.List;

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
@Table(name = "PERSON")
public class PersonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PERSON_ID")
	private Long personId;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name="FULL_NAME", nullable=false)
	private String fullName;

	@Column(name = "USER_NAME", nullable = false,unique = true)
	private String userName;

	@JsonIgnore
	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@ManyToOne
	@JoinColumn(name = "COUNTRY_CODE")
	private CountryEntity country;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CREATE_TIMESTAMP", nullable = false)
	private Timestamp createTimestamp;

	@Column(name = "UPDATE_TIMESTAMP", nullable = false)
	private Timestamp updateTimestamp;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name="DESIGNATION")
	private String designation;

	@OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<PersonRoleEntity> roles;
	
}
