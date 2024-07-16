package com.example.polusServiceRequest.DTOs;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CountryDTO {

	private String countryCode;
	private String countryName;
	private String currencyCode;
	private Timestamp updateTimestamp;
	private String updateUser;
	private String countryCodeIso2;

}
