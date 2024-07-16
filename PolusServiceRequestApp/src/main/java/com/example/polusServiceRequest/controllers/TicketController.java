package com.example.polusServiceRequest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.polusServiceRequest.DTOs.AdminDTO;
import com.example.polusServiceRequest.DTOs.CountryDTO;
import com.example.polusServiceRequest.DTOs.NewServiceTicketDTO;
import com.example.polusServiceRequest.DTOs.SRTicketCategoryDTO;
import com.example.polusServiceRequest.DTOs.TicketResponseDTO;
import com.example.polusServiceRequest.models.SRTicketCategoryEntity;
import com.example.polusServiceRequest.models.SRTicketsEntity;
import com.example.polusServiceRequest.services.LoginService;
import com.example.polusServiceRequest.services.SRTicketCategoryService;
import com.example.polusServiceRequest.services.SRTicketService;

@RestController
@RequestMapping("/api")
public class TicketController {

	@Autowired
	private SRTicketCategoryService categoryService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private SRTicketService ticketService;

	@GetMapping("/getAllServiceCategories")
	public ResponseEntity<List<SRTicketCategoryEntity>> getServiceCategories() {
		try {
			List<SRTicketCategoryEntity> categories = categoryService.getAllCategories();
			return ResponseEntity.ok().body(categories);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/getAllAdmins")
	public ResponseEntity<List<AdminDTO>> getApplicationAdministrators() {
		try {
			List<AdminDTO> administrators = loginService.getAllApplicationAdministrators();
			return ResponseEntity.ok().body(administrators);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/createServiceTicket")
	public ResponseEntity<Object> createServiceTicket(@RequestBody NewServiceTicketDTO ticketDTO) {
		try {
			TicketResponseDTO ticket = ticketService.createServiceTicket(ticketDTO);
			return ResponseEntity.ok().body(ticket);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to create service ticket: " + e.getMessage());
		}
	}
}
