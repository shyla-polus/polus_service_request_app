package com.example.polusServiceRequest.DTOs;

import lombok.Data;

@Data
public class UpdateServiceTicketDTO {

	private Long ticketId;
	private String category;
	private String requestDescription;

}
