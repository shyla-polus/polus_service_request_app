package com.example.polusServiceRequest.DTOs;

import lombok.Data;

@Data
public class ServiceTicketsDetailsDTO {

	private Long personID;
	private Long statusType;
	private Long pageNumber;
	private Long pageSize;

}
