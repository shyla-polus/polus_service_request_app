package com.example.polusServiceRequest.DTOs;

import lombok.Data;

@Data
public class TicketApproveOrRejectDTO {
	
	private Long ticketId;
	private String comment;
	private Long statusCode;

}
