package com.example.polusServiceRequest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.polusServiceRequest.models.SRTicketStatusEntity;

public interface SRTicketStatusRepository extends JpaRepository<SRTicketStatusEntity, Long> {

}
