package com.example.polusServiceRequest.DTOs;

import lombok.Data;

@Data
public class TicketResponseDTO {

	private Long ticketId;
	private String categoryName;
	private String description;
	private String statusDescription;
	private String assignedTo;
	
}