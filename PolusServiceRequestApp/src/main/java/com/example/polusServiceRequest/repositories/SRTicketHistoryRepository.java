package com.example.polusServiceRequest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.polusServiceRequest.models.SRTicketHistoryEntity;

public interface SRTicketHistoryRepository extends JpaRepository<SRTicketHistoryEntity, Long>{

}
