package com.example.polusServiceRequest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.polusServiceRequest.DTOs.SRTicketCategoryDTO;
import com.example.polusServiceRequest.models.SRTicketCategoryEntity;
import com.example.polusServiceRequest.repositories.SRTicketCategoryRepository;

@Service
public class SRTicketCategoryServiceImpl implements SRTicketCategoryService {

	@Autowired
	private SRTicketCategoryRepository categoryRepository;

	@Override
	public List<SRTicketCategoryEntity> getAllCategories() {
		List<SRTicketCategoryEntity> categories = categoryRepository.findAll();
		return categories;
//		List<SRTicketCategoryDTO> categoryDTOs = new ArrayList<>();
//		for (SRTicketCategoryEntity category : categories) {
//			SRTicketCategoryDTO dto = new SRTicketCategoryDTO();
//			dto.setCategoryCode(category.getCategoryCode());
//			dto.setCategoryName(category.getCategoryName());
//			dto.setDescription(category.getDescription());
//			categoryDTOs.add(dto);
//		}
//		return categoryDTOs;
}
}
	