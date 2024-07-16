package com.example.polusServiceRequest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.polusServiceRequest.models.SRTicketCategoryEntity;

public interface SRTicketCategoryRepository extends JpaRepository<SRTicketCategoryEntity, String>  {

}
