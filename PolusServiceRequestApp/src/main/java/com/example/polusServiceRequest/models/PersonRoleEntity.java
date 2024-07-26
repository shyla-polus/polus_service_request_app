package com.example.polusServiceRequest.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "PERSON_ROLE")
public class PersonRoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PERSON_ROLE_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
	private PersonEntity person;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
	private RoleEntity role;

	@ManyToOne
	@JoinColumn(name = "UPDATE_USER")
	private PersonEntity updateUser;

	@Column(name = "UPDATE_TIMESTAMP")
	private Timestamp updateTimestamp;

}
