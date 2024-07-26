package com.example.polusServiceRequest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.polusServiceRequest.DTOs.MakeOrRemoveAdminDTO;
import com.example.polusServiceRequest.DTOs.NewServiceCategoryDTO;
import com.example.polusServiceRequest.DTOs.PersonDTO;
import com.example.polusServiceRequest.DTOs.RequestCountsDTO;
import com.example.polusServiceRequest.DTOs.RoleDTO;
import com.example.polusServiceRequest.DTOs.SRTicketCategoryDTO;
import com.example.polusServiceRequest.DTOs.ServiceTicketDTO;
import com.example.polusServiceRequest.DTOs.ServiceTicketsDetailsDTO;
import com.example.polusServiceRequest.DTOs.SignInResponseDTO;
import com.example.polusServiceRequest.DTOs.SignUpDTO;
import com.example.polusServiceRequest.DTOs.StatusDTO;
import com.example.polusServiceRequest.DTOs.TicketApproveOrRejectDTO;
import com.example.polusServiceRequest.DTOs.TicketResponseDTO;
import com.example.polusServiceRequest.services.SRTicketCategoryService;
import com.example.polusServiceRequest.services.SRTicketService;

@RestController
@RequestMapping("/api")
public class TicketController {

	@Autowired
	private SRTicketCategoryService categoryService;

	@Autowired
	private SRTicketService ticketService;

	@GetMapping("/getAllServiceCategories")
	public ResponseEntity<List<SRTicketCategoryDTO>> getServiceCategories() {
		try {
			List<SRTicketCategoryDTO> categories = categoryService.getAllCategories();
			return ResponseEntity.ok().body(categories);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@DeleteMapping("/deleteServiceTicket/{ticketId}")
	public ResponseEntity<?> deleteServiceTicket(@PathVariable Long ticketId) {
		Map<String, String> response = new HashMap<>();
		try {
			Boolean actionCompleted = ticketService.deleteServiceTicket(ticketId);

			if (actionCompleted) {
				response.put("message", "Ticket Deleted Successfully.");
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} else {
				response.put("message", "Failed to delete the ticket ");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
		}
	}

	@PostMapping("getServiceTickets")
	public ResponseEntity<List<TicketResponseDTO>> getServiceTickets(
			@RequestBody ServiceTicketsDetailsDTO serviceTicketsDetailsDTO) {

		try {
			List<TicketResponseDTO> tickets = ticketService.getServiceTickets(serviceTicketsDetailsDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(tickets);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@PostMapping("/createOrEditServiceTicket")
	public ResponseEntity<Object> createOrEditServiceTicket(@RequestBody ServiceTicketDTO ticketDTO) {
		try {
			return ticketService.createOrEditTicket(ticketDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error :" + e.getMessage());
		}
	}

	@PostMapping("/ticketStatusChangeToAssigned")
	public ResponseEntity<Object> ticketStatusChangeToAssigned(@RequestBody ServiceTicketDTO ticketDTO) {
		Map<String, String> response = new HashMap<>();
		try {
			boolean actionCompleted = ticketService.setTicketStatusAssigned(ticketDTO);
			if (actionCompleted) {
				response.put("message", "Ticket status changed to Assigned.");
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} else {
				response.put("message", "Failed to change the ticket status to assigned.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}

	}

	@PostMapping("/statusChangeToApprovedOrRejected")
	public ResponseEntity<Object> ticketStatusChangeTOApprovedOrRejected(
			@RequestBody TicketApproveOrRejectDTO ticketDTO) {
		Map<String, String> response = new HashMap<>();
		try {
			boolean actionCompleted = ticketService.approveOrRejectTicket(ticketDTO);
			if (actionCompleted) {
				response.put("message", "Ticket status changed to Approved/Rejected.");
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} else {
				response.put("message", "Failed to change the ticket status to approved/rejected.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}

	}
	
	@PostMapping("/editApprovedOrRejectedTickets")
	public ResponseEntity<Object> editApprovedOrRejectedTickets(
			@RequestBody TicketApproveOrRejectDTO ticketDTO) {
		Map<String, String> response = new HashMap<>();
		try {
			boolean actionCompleted = ticketService.editApprovedOrRejectedTickets(ticketDTO);
			if (actionCompleted) {
				response.put("message", "Ticket status edited successfully.");
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} else {
				response.put("message", "Failed");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}

	}

	@GetMapping("/getAllUsersByRoleId/{roleID}")
	public ResponseEntity<List<PersonDTO>> getAllUsersByRoleId(@PathVariable("roleID") Long roleID) {
		try {
			List<PersonDTO> normalUsers = ticketService.getAllUsersByroleId(roleID);
			return ResponseEntity.ok().body(normalUsers);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/getAllRequestCountsByPersonID/{personID}")
	public ResponseEntity<RequestCountsDTO> getAllRequestsCountsByPersonID(@PathVariable("personID") Long personID) {
		try {
			RequestCountsDTO countsDTO = ticketService.getRequestCounts(personID);
			return ResponseEntity.ok().body(countsDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/createNewServiceCategory")
	public ResponseEntity<Object> createNewServiceCategory(@RequestBody NewServiceCategoryDTO categoryDTO) {
		Map<String, String> response = new HashMap<>();
		try {
			boolean actionCompleted = ticketService.CreateNewServiceCategory(categoryDTO);
			if (actionCompleted) {
				response.put("message", "New service created successfully");
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} else {
				response.put("message", "Failed to create new service category");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

	@PostMapping("/makeAdmin")
	public ResponseEntity<Object> MakeAdmin(@RequestBody MakeOrRemoveAdminDTO makeAdminDTO) {
		Map<String, String> response = new HashMap<>();
		try {
			boolean actionCompleted = ticketService.MakeAdmin(makeAdminDTO);
			if (actionCompleted) {
				response.put("message", "Role assigned Successfully");
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} else {
				response.put("message", "Role assigned Failed");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

	@DeleteMapping("/removeAdmin")
	public ResponseEntity<Object> removeAdmin(@RequestBody MakeOrRemoveAdminDTO makeAdminDTO) {
		Map<String, String> response = new HashMap<>();
		try {
			boolean actionCompleted = ticketService.removeAdmin(makeAdminDTO);
			if (actionCompleted) {
				response.put("message", "Admin privilege removed successfully");
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.put("message", "Failed to remove admin privilege");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

	@GetMapping("/getAllServiceTicketsByPersonID/{personID}")
	public ResponseEntity<List<TicketResponseDTO>> getAllServiceTicketsByPersonID(
			@PathVariable("personID") Long personID) {
		try {
			List<TicketResponseDTO> ticketResponseDTO = ticketService.getAllServiceTicketsByPersonID(personID);
			return ResponseEntity.ok().body(ticketResponseDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/getAllStatuses")
	public ResponseEntity<List<StatusDTO>> getAllStatuses() {
		try {
			List<StatusDTO> statuses = ticketService.getAllStatuses();
			return ResponseEntity.ok().body(statuses);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/editUserDetails")
	public ResponseEntity<Object> editUserDetails(@RequestBody SignUpDTO signUpDTO) {
		try {

			SignInResponseDTO responseDTO = ticketService.editUserDetails(signUpDTO);
			return ResponseEntity.ok().body(responseDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("getAllAssignedToMeTickets")
	public ResponseEntity<List<TicketResponseDTO>> getAllAssignedToMeTickets(
			@RequestBody ServiceTicketsDetailsDTO serviceTicketsDetailsDTO) {

		try {
			List<TicketResponseDTO> tickets = ticketService.getAllAssignedToMeTickets(serviceTicketsDetailsDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(tickets);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
	
	@GetMapping("/getAllRoles")
	public ResponseEntity<List<RoleDTO>> getAllRoles() {
		try {
			List<RoleDTO> statuses = ticketService.getAllRoles();
			return ResponseEntity.ok().body(statuses);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
