package com.example.polusServiceRequest.services;

import java.util.List;

import com.example.polusServiceRequest.DTOs.SRTicketCategoryDTO;
import com.example.polusServiceRequest.models.SRTicketCategoryEntity;

public interface SRTicketCategoryService {
	
	List<SRTicketCategoryEntity> getAllCategories();

}
