package com.example.polusServiceRequest.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.polusServiceRequest.DTOs.MakeOrRemoveAdminDTO;
import com.example.polusServiceRequest.DTOs.NewServiceCategoryDTO;
import com.example.polusServiceRequest.DTOs.PersonDTO;
import com.example.polusServiceRequest.DTOs.RequestCountsDTO;
import com.example.polusServiceRequest.DTOs.RoleDTO;
import com.example.polusServiceRequest.DTOs.ServiceTicketDTO;
import com.example.polusServiceRequest.DTOs.ServiceTicketsDetailsDTO;
import com.example.polusServiceRequest.DTOs.SignInResponseDTO;
import com.example.polusServiceRequest.DTOs.SignUpDTO;
import com.example.polusServiceRequest.DTOs.StatusDTO;
import com.example.polusServiceRequest.DTOs.TicketApproveOrRejectDTO;
import com.example.polusServiceRequest.DTOs.TicketResponseDTO;

public interface SRTicketService {

	Boolean deleteServiceTicket(Long ticketId);

	List<TicketResponseDTO> getServiceTickets(ServiceTicketsDetailsDTO serviceTicketsDetailsDTO);

	ResponseEntity<Object> createOrEditTicket(ServiceTicketDTO ticketDTO);

	Boolean setTicketStatusAssigned(ServiceTicketDTO ticketDTO);

	Boolean approveOrRejectTicket(TicketApproveOrRejectDTO ticketDTO);

	RequestCountsDTO getRequestCounts(Long personId);

	Boolean CreateNewServiceCategory(NewServiceCategoryDTO categoryDTO);

	Boolean MakeAdmin(MakeOrRemoveAdminDTO makeOrRemoveAdminDTO);

	List<TicketResponseDTO> getAllServiceTicketsByPersonID(Long personID);

	Boolean removeAdmin(MakeOrRemoveAdminDTO makeOrRemoveAdminDTO);

	List<TicketResponseDTO> getAllAssignedToMeTickets(ServiceTicketsDetailsDTO serviceTicketsDetailsDTO);

	List<StatusDTO> getAllStatuses();

	SignInResponseDTO editUserDetails(SignUpDTO userDetails);

	List<PersonDTO> getAllUsersByroleId(Long roleId);

	List<RoleDTO> getAllRoles();

	Boolean editApprovedOrRejectedTickets(TicketApproveOrRejectDTO ticketDTO);
}
