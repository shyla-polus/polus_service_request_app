package com.example.polusServiceRequest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.polusServiceRequest.models.SRTicketsEntity;

public interface SRTicketsRepository extends JpaRepository<SRTicketsEntity, Long> {

}
