package com.example.polusServiceRequest.DTOs;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class TicketResponseDTO {

	private Long ticketId;
	private SRTicketCategoryDTO category;
	private String requestDescription;
	private StatusDTO statusDescription;
	private PersonDTO assignedTo;
	private Timestamp createTimestamp;
	private Timestamp updateTimestamp;
	private CommentDetailsDTO comment;
}