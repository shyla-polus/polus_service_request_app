package com.example.polusServiceRequest.DTOs;

import lombok.Data;

@Data
public class NewServiceTicketDTO {

	private Long personId;
	private String category;
	private Long assignedTo;
	private String description;

}