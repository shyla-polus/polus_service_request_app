package com.example.polusServiceRequest.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.polusServiceRequest.DTOs.NewServiceTicketDTO;
import com.example.polusServiceRequest.DTOs.TicketResponseDTO;
import com.example.polusServiceRequest.constants.SRTicketStatusConstants;
import com.example.polusServiceRequest.models.PersonEntity;
import com.example.polusServiceRequest.models.SRTicketCategoryEntity;
import com.example.polusServiceRequest.models.SRTicketHistoryEntity;
import com.example.polusServiceRequest.models.SRTicketStatusEntity;
import com.example.polusServiceRequest.models.SRTicketsEntity;
import com.example.polusServiceRequest.repositories.PersonRepository;
import com.example.polusServiceRequest.repositories.SRTicketCategoryRepository;
import com.example.polusServiceRequest.repositories.SRTicketHistoryRepository;
import com.example.polusServiceRequest.repositories.SRTicketStatusRepository;
import com.example.polusServiceRequest.repositories.SRTicketsRepository;

import jakarta.transaction.Transactional;

@Service
public class SRTicketServiceImpl implements SRTicketService {

	@Autowired
	private SRTicketsRepository ticketsRepository;

	@Autowired
	private SRTicketHistoryRepository historyRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private SRTicketStatusRepository statusRepository;

	@Autowired
	private SRTicketCategoryRepository categoryRepository; // Add category repository

	@Override
	public TicketResponseDTO createServiceTicket(NewServiceTicketDTO ticketDTO) {

		PersonEntity user = personRepository.findById(ticketDTO.getPersonId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		PersonEntity assignedTo = personRepository.findById(ticketDTO.getAssignedTo())
				.orElseThrow(() -> new RuntimeException("Assigned user not found"));
		SRTicketCategoryEntity category = categoryRepository.findById(ticketDTO.getCategory())
				.orElseThrow(() -> new RuntimeException("Category not found"));

		SRTicketsEntity ticket = new SRTicketsEntity();
		ticket.setPerson(user);
		ticket.setCategory(category);
		ticket.setAssignedTo(assignedTo);
		ticket.setDescription(ticketDTO.getDescription());
		ticket.setCreateTimestamp(Timestamp.from(Instant.now()));
		ticket.setUpdateTimestamp(ticket.getCreateTimestamp()); // Initial update timestamp same as create timestamp
		SRTicketStatusEntity initialStatus = statusRepository.findByStatusCode(SRTicketStatusConstants.IN_PROGRESS);
		if (initialStatus == null) {
			throw new RuntimeException("Initial status not found");
		}
		ticket.setStatus(initialStatus);
		ticket = ticketsRepository.save(ticket);
		SRTicketHistoryEntity history = new SRTicketHistoryEntity();
		history.setSrTicket(ticket);
		history.setStatusType(initialStatus); // Assuming statusType in history is status itself
		history.setUpdateUser(user);
		history.setUpdateTimestamp(Timestamp.from(Instant.now()));
		historyRepository.save(history);

		// Construct and return response DTO
		TicketResponseDTO responseDTO = new TicketResponseDTO();
		responseDTO.setTicketId(ticket.getSrTicketId());
		responseDTO.setCategoryName(ticket.getCategory().getCategoryName());
		responseDTO.setDescription(ticket.getDescription());
		responseDTO.setStatusDescription(ticket.getStatus().getStatusDescription());
		responseDTO.setAssignedTo(ticket.getAssignedTo() != null
				? ticket.getAssignedTo().getFirstName() + " " + ticket.getAssignedTo().getLastName()
				: null); // Handle null assignedTo
		return responseDTO;
	}
}
