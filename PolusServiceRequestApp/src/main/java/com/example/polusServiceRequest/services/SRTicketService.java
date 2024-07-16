package com.example.polusServiceRequest.services;

import com.example.polusServiceRequest.DTOs.NewServiceTicketDTO;
import com.example.polusServiceRequest.DTOs.TicketResponseDTO;

public interface SRTicketService {
	
	TicketResponseDTO createServiceTicket(NewServiceTicketDTO ticketDTO);
	
}
