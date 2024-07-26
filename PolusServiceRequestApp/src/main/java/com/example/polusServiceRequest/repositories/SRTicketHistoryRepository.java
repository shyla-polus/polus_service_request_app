package com.example.polusServiceRequest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.polusServiceRequest.models.SRTicketHistoryEntity;
import com.example.polusServiceRequest.models.SRTicketsEntity;

import jakarta.transaction.Transactional;

public interface SRTicketHistoryRepository extends JpaRepository<SRTicketHistoryEntity, Long> {

	@Transactional
	void deleteBySrTicket(SRTicketsEntity srTicket);

}
