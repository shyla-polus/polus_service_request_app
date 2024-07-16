package com.example.polusServiceRequest.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "country")
public class CountryEntity {

	@Id
	@Column(name = "COUNTRY_CODE")
	private String countryCode;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

	@Column(name = "CURRENCY_CODE")
	private String currencyCode;

	@Column(name = "UPDATE_TIMESTAMP")
	private Timestamp updateTimestamp;

	@Column(name = "UPDATE_USER")
	private String updateUser;

	@Column(name = "COUNTRY_CODE_ISO2")
	private String countryCodeIso2;

}
