package com.example.polusServiceRequest.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.polusServiceRequest.DTOs.CommentDetailsDTO;
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
import com.example.polusServiceRequest.constants.RoleNamesConstants;
import com.example.polusServiceRequest.constants.SRTicketStatusConstants;
import com.example.polusServiceRequest.models.CountryEntity;
import com.example.polusServiceRequest.models.PersonEntity;
import com.example.polusServiceRequest.models.PersonRoleEntity;
import com.example.polusServiceRequest.models.RoleEntity;
import com.example.polusServiceRequest.models.SRTicketCategoryEntity;
import com.example.polusServiceRequest.models.SRTicketCommentsEntity;
import com.example.polusServiceRequest.models.SRTicketHistoryEntity;
import com.example.polusServiceRequest.models.SRTicketStatusEntity;
import com.example.polusServiceRequest.models.SRTicketsEntity;
import com.example.polusServiceRequest.repositories.CountryRepository;
import com.example.polusServiceRequest.repositories.PersonRepository;
import com.example.polusServiceRequest.repositories.PersonRoleRepository;
import com.example.polusServiceRequest.repositories.RoleRepository;
import com.example.polusServiceRequest.repositories.SRTicketCategoryRepository;
import com.example.polusServiceRequest.repositories.SRTicketCommentsRepository;
import com.example.polusServiceRequest.repositories.SRTicketHistoryRepository;
import com.example.polusServiceRequest.repositories.SRTicketStatusRepository;
import com.example.polusServiceRequest.repositories.SRTicketsRepository;

@Service
public class SRTicketServiceImpl implements SRTicketService {

	private Logger logger = LogManager.getLogger(SRTicketServiceImpl.class);

	@Autowired
	private SRTicketsRepository ticketsRepository;

	@Autowired
	private SRTicketHistoryRepository historyRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private SRTicketStatusRepository statusRepository;

	@Autowired
	private SRTicketCategoryRepository categoryRepository;

	@Autowired
	private SRTicketCommentsRepository commentRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PersonRoleRepository personRoleRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Override
	public Boolean deleteServiceTicket(Long ticketId) {
		try {
			SRTicketsEntity ticket = ticketsRepository.findById(ticketId)
					.orElseThrow(() -> new RuntimeException("Ticket not found"));
			historyRepository.deleteBySrTicket(ticket);
			ticketsRepository.delete(ticket);
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<TicketResponseDTO> getServiceTickets(ServiceTicketsDetailsDTO serviceTicketsDetailsDTO) {

		try {
			Long offset = serviceTicketsDetailsDTO.getPageNumber() * serviceTicketsDetailsDTO.getPageSize();
			List<SRTicketsEntity> tickets = ticketsRepository.findServiceTicketsByPersonId(
					serviceTicketsDetailsDTO.getPersonID(), serviceTicketsDetailsDTO.getStatusType(),
					serviceTicketsDetailsDTO.getPageSize(), offset);
			List<TicketResponseDTO> ticketDTOs = new ArrayList<>();

			for (SRTicketsEntity ticket : tickets) {
				TicketResponseDTO dto = new TicketResponseDTO();
				dto.setTicketId(ticket.getSrTicketId());
				dto.setCategory(getCategoryDetails(ticket.getCategory()));
				dto.setRequestDescription(ticket.getDescription());
				dto.setStatusDescription(getStatusDetails(ticket.getStatus()));//
				dto.setCreateTimestamp(ticket.getCreateTimestamp());
				dto.setUpdateTimestamp(ticket.getUpdateTimestamp());
				Long statusType = serviceTicketsDetailsDTO.getStatusType();
				if (!statusType.equals(SRTicketStatusConstants.IN_PROGRESS))
					dto.setAssignedTo(getAdminDetails(ticket.getAssignedTo()));
				if (statusType.equals(SRTicketStatusConstants.APPROVED)
						|| statusType.equals(SRTicketStatusConstants.REJECTED)) {
					SRTicketCommentsEntity comment = commentRepository.findByTicketId(ticket.getSrTicketId());
					if (comment != null) {
						CommentDetailsDTO commentDetailsDTO = new CommentDetailsDTO();
						commentDetailsDTO.setCommentId(comment.getCommentId());
						commentDetailsDTO.setComment(comment.getComment());
						commentDetailsDTO.setCommentUser(getAdminDetails(comment.getCommentUser()));
						commentDetailsDTO.setCommentTimestamp(comment.getCommentTimestamp());
						dto.setComment(commentDetailsDTO);
					}
				}
				ticketDTOs.add(dto);
			}
			return ticketDTOs;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Object> createOrEditTicket(ServiceTicketDTO ticketDTO) {

		try {
			boolean actionCompleted;
			Map<String, String> response = new HashMap<>();

			if (ticketDTO.getTicketId() == null) {
				actionCompleted = createNewServiceTicket(ticketDTO);
				if (actionCompleted)
					response.put("message", "Ticket Created Successfully.");
				else
					response.put("message", "Ticket Created Failed.");
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}
			actionCompleted = updateInProgressTicket(ticketDTO);
			if (actionCompleted)
				response.put("message", "Ticket Edited Successfully.");
			else
				response.put("message", "Ticket Edited Failed.");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());

		}
	}

	@Override
	public Boolean setTicketStatusAssigned(ServiceTicketDTO ticketDTO) {

		try {
			SRTicketsEntity ticket = ticketsRepository.findById(ticketDTO.getTicketId())
					.orElseThrow(() -> new RuntimeException("Ticket not found"));

			PersonEntity admin = personRepository.findById(ticketDTO.getAssignedTo())
					.orElseThrow(() -> new RuntimeException("Admin not found"));

			SRTicketStatusEntity status = statusRepository.findById(SRTicketStatusConstants.ASSIGNED)
					.orElseThrow(() -> new RuntimeException("Status not found"));

			ticket.setAssignedTo(admin);
			ticket.setUpdateTimestamp(Timestamp.from(Instant.now()));
			ticket.setStatus(status);
			ticket = ticketsRepository.save(ticket);
			saveTicketHistory(ticket);
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Boolean approveOrRejectTicket(TicketApproveOrRejectDTO ticketDTO) {

		try {
			SRTicketsEntity ticket = ticketsRepository.findById(ticketDTO.getTicketId())
					.orElseThrow(() -> new RuntimeException("Ticket not found"));
			SRTicketStatusEntity status = statusRepository.findById(ticketDTO.getStatusCode())
					.orElseThrow(() -> new RuntimeException("Status not found"));
			ticket.setStatus(status);
			ticket.setUpdateTimestamp(Timestamp.from(Instant.now()));
			ticket = ticketsRepository.save(ticket);

			saveTicketHistory(ticket);

			if (ticketDTO.getComment() != null && !ticketDTO.getComment().isEmpty()) {
				SRTicketCommentsEntity comment = new SRTicketCommentsEntity();
				comment.setSrTicket(ticket);
				comment.setComment(ticketDTO.getComment());
				comment.setCommentUser(ticket.getAssignedTo());
				comment.setCommentTimestamp(Timestamp.from(Instant.now()));
				commentRepository.save(comment);
			}
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Boolean editApprovedOrRejectedTickets(TicketApproveOrRejectDTO ticketDTO) {
		try {
			SRTicketsEntity ticket = ticketsRepository.findById(ticketDTO.getTicketId())
					.orElseThrow(() -> new RuntimeException("Ticket not found"));
			// Validation set here
			SRTicketStatusEntity status = statusRepository.findById(ticketDTO.getStatusCode())
					.orElseThrow(() -> new RuntimeException("Status not found"));
			ticket.setStatus(status);
			ticket.setUpdateTimestamp(Timestamp.from(Instant.now()));
			ticket = ticketsRepository.save(ticket);

			saveTicketHistory(ticket);
			// Fetch the existing comment
			SRTicketCommentsEntity comment = commentRepository.findByTicketId(ticket.getSrTicketId());

			// If comment doesn't exist, create a new one
			if (comment == null) {
				comment = new SRTicketCommentsEntity();
			}

			comment.setSrTicket(ticket);
			comment.setComment(ticketDTO.getComment());
			comment.setCommentUser(ticket.getAssignedTo());
			comment.setCommentTimestamp(Timestamp.from(Instant.now()));
			commentRepository.save(comment);

			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public RequestCountsDTO getRequestCounts(Long personId) {

		try {
			RequestCountsDTO countDTO = new RequestCountsDTO();
			countDTO.setTotalRequests(ticketsRepository.countAllRequestsByPersonId(personId));
			countDTO.setInProgressRequests(
					ticketsRepository.requestsCount(personId, SRTicketStatusConstants.IN_PROGRESS));
			countDTO.setAssignedRequests(ticketsRepository.requestsCount(personId, SRTicketStatusConstants.ASSIGNED));
			countDTO.setApprovedRequests(ticketsRepository.requestsCount(personId, SRTicketStatusConstants.APPROVED));
			countDTO.setRejectedRequests(ticketsRepository.requestsCount(personId, SRTicketStatusConstants.REJECTED));
			countDTO.setAssignedToMeRequests(
					ticketsRepository.countAssignedToMeTickets(personId, SRTicketStatusConstants.ASSIGNED));
			countDTO.setAdminApproveRequests(
					ticketsRepository.countAssignedToMeTickets(personId, SRTicketStatusConstants.APPROVED));
			countDTO.setAdminRejectRequests(
					ticketsRepository.countAssignedToMeTickets(personId, SRTicketStatusConstants.REJECTED));
			return countDTO;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Boolean CreateNewServiceCategory(NewServiceCategoryDTO categoryDTO) {
		try {
			PersonEntity admin = personRepository.findById(categoryDTO.getAdminId())
					.orElseThrow(() -> new RuntimeException("Admin not found"));
			SRTicketCategoryEntity category = new SRTicketCategoryEntity();
			category.setCategoryName(categoryDTO.getCategoryName());
			category.setDescription(categoryDTO.getDescription());
			category.setUpdateUser(admin);
			category.setUpdateTimestamp(Timestamp.from(Instant.now()));
			category.setIsActive("yes");
			categoryRepository.save(category);
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Boolean MakeAdmin(MakeOrRemoveAdminDTO makeAdminDTO) {
		try {
			PersonEntity person = personRepository.findById(makeAdminDTO.getPersonId())
					.orElseThrow(() -> new RuntimeException("User not found"));
			RoleEntity role = roleRepository.findById(makeAdminDTO.getRole())
					.orElseThrow(() -> new RuntimeException("Role not found"));
			PersonEntity admin = personRepository.findById(makeAdminDTO.getAdminID())
					.orElseThrow(() -> new RuntimeException("Admin not found"));
			PersonRoleEntity personRoleEntity = new PersonRoleEntity();
			personRoleEntity.setPerson(person);
			personRoleEntity.setRole(role);
			personRoleEntity.setUpdateUser(admin);
			personRoleEntity.setUpdateTimestamp(Timestamp.from(Instant.now()));
			personRoleRepository.save(personRoleEntity);
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<TicketResponseDTO> getAllServiceTicketsByPersonID(Long personID) {

		try {
			List<SRTicketsEntity> tickets = ticketsRepository.findAllRequestsByPersonId(personID);
			List<TicketResponseDTO> ticketDTOs = new ArrayList<>();
			for (SRTicketsEntity ticket : tickets) {
				TicketResponseDTO dto = new TicketResponseDTO();
				dto.setTicketId(ticket.getSrTicketId());
				dto.setCategory(getCategoryDetails(ticket.getCategory()));
				dto.setRequestDescription(ticket.getDescription());
				dto.setStatusDescription(getStatusDetails(ticket.getStatus()));//
				dto.setCreateTimestamp(ticket.getCreateTimestamp());
				dto.setUpdateTimestamp(ticket.getUpdateTimestamp());
				Long statusType = ticket.getStatus().getStatusCode();
				if (!statusType.equals(SRTicketStatusConstants.IN_PROGRESS))
					dto.setAssignedTo(getAdminDetails(ticket.getAssignedTo()));
				ticketDTOs.add(dto);
			}
			return ticketDTOs;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Boolean removeAdmin(MakeOrRemoveAdminDTO makeOrRemoveAdminDTO) {
		try {

			Long personId = personRoleRepository.findPersonRoleIdByPersonIdAndRoleId(makeOrRemoveAdminDTO.getAdminID(),
					makeOrRemoveAdminDTO.getRole());
			if (personId == null) {
				throw new RuntimeException("Admin not found/ You have no privilege to remove the role!");
			}
			personId = personRoleRepository.findPersonRoleIdByPersonIdAndRoleId(makeOrRemoveAdminDTO.getPersonId(),
					makeOrRemoveAdminDTO.getRole());
			if (personId == null) {
				throw new RuntimeException("This user is not already an admin!");
			}
			// Remove the admin privilege
			personRoleRepository.removeAdminPrivilege(makeOrRemoveAdminDTO.getPersonId(),
					makeOrRemoveAdminDTO.getRole());
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<StatusDTO> getAllStatuses() {
		try {

			List<SRTicketStatusEntity> statuses = statusRepository.findAll();
			List<StatusDTO> statusDTOs = new ArrayList<>();
			for (SRTicketStatusEntity status : statuses) {
				statusDTOs.add(getStatusDetails(status));
			}
			return statusDTOs;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public SignInResponseDTO editUserDetails(SignUpDTO userDetails) {

		try {
			PersonEntity person = personRepository.findById(userDetails.getPersonId())
					.orElseThrow(() -> new RuntimeException("Ticket not found"));
			person.setFirstName(userDetails.getFirstName());
			person.setLastName(userDetails.getLastName());
			person.setFullName(userDetails.getFirstName() + " " + userDetails.getLastName());
			if (!userDetails.getUserName().equals(person.getUserName())) {
				// Check if the username already exists
				if (personRepository.findByUserName(userDetails.getUserName()) != null) {
					throw new RuntimeException("Username already exists");
				}
			}
			person.setUserName(userDetails.getUserName());
			person.setEmail(userDetails.getEmail());
			person.setPhoneNumber(userDetails.getPhoneNo());
			person.setAddress(userDetails.getAddress());
			person.setUpdateTimestamp(Timestamp.from(Instant.now()));
			CountryEntity country = countryRepository.findById(userDetails.getCountry())
					.orElseThrow(() -> new RuntimeException("Country not found"));
			person.setCountry(country);
			person.setDesignation(userDetails.getDesignation());
			PersonEntity savedPerson = personRepository.save(person);
			return getUserDetails(savedPerson);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<TicketResponseDTO> getAllAssignedToMeTickets(ServiceTicketsDetailsDTO serviceTicketsDetailsDTO) {
		try {
			Long offset = serviceTicketsDetailsDTO.getPageNumber() * serviceTicketsDetailsDTO.getPageSize();
			List<SRTicketsEntity> tickets = ticketsRepository.findAllAssignedToMeTickets(
					serviceTicketsDetailsDTO.getPersonID(), serviceTicketsDetailsDTO.getStatusType(),
					serviceTicketsDetailsDTO.getPageSize(), offset);
			List<TicketResponseDTO> ticketDTOs = new ArrayList<>();
			for (SRTicketsEntity ticket : tickets) {
				TicketResponseDTO dto = new TicketResponseDTO();
				dto.setTicketId(ticket.getSrTicketId());
				dto.setCategory(getCategoryDetails(ticket.getCategory()));
				dto.setRequestDescription(ticket.getDescription());
				dto.setStatusDescription(getStatusDetails(ticket.getStatus()));//
				dto.setCreateTimestamp(ticket.getCreateTimestamp());
				dto.setUpdateTimestamp(ticket.getUpdateTimestamp());
				Long statusType = serviceTicketsDetailsDTO.getStatusType();
				if (!statusType.equals(SRTicketStatusConstants.IN_PROGRESS))
					dto.setAssignedTo(getAdminDetails(ticket.getAssignedTo()));
				if (statusType.equals(SRTicketStatusConstants.APPROVED)
						|| statusType.equals(SRTicketStatusConstants.REJECTED)) {
					SRTicketCommentsEntity comment = commentRepository.findByTicketId(ticket.getSrTicketId());
					if (comment != null) {
						CommentDetailsDTO commentDetailsDTO = new CommentDetailsDTO();
						commentDetailsDTO.setCommentId(comment.getCommentId());
						commentDetailsDTO.setComment(comment.getComment());
						commentDetailsDTO.setCommentUser(getAdminDetails(comment.getCommentUser()));
						commentDetailsDTO.setCommentTimestamp(comment.getCommentTimestamp());
						dto.setComment(commentDetailsDTO);
					}
				}
				ticketDTOs.add(dto);
			}
			return ticketDTOs;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<PersonDTO> getAllUsersByroleId(Long roleId) {

		List<PersonEntity> users = null;
		try {
			if (roleId == RoleNamesConstants.APPLICATION_ADMINISTRATOR) {
				users = personRepository.findAllApplicationAdministrators();

			} else if (roleId == RoleNamesConstants.PRINCIPAL_INVESTIGATOR) {
				users = personRepository.findPrincipalInvestigatorsNotAdmins();
			}
			List<PersonDTO> personDTOs = new ArrayList<>();
			for (PersonEntity person : users) {
				personDTOs.add(getAdminDetails(person));
			}
			return personDTOs;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public List<RoleDTO> getAllRoles() {
		List<RoleEntity> roles = roleRepository.findAll();
		List<RoleDTO> roleDTOs = new ArrayList<>();
		for (RoleEntity role : roles) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setRoleId(role.getRoleId());
			roleDTO.setRoleName(role.getRoleName());
			roleDTO.setRoleDescription(role.getRoleDescription());
			roleDTOs.add(roleDTO);
		}
		return roleDTOs;
	}

	private SignInResponseDTO getUserDetails(PersonEntity person) {

		SignInResponseDTO responseDTO = new SignInResponseDTO();
		responseDTO.setPersonId(person.getPersonId());
		responseDTO.setFirstName(person.getFirstName());
		responseDTO.setLastName(person.getLastName());
		responseDTO.setUserName(person.getUserName());
		responseDTO.setEmail(person.getEmail());
		responseDTO.setCountry(person.getCountry());
		responseDTO.setPhoneNumber(person.getPhoneNumber());
		responseDTO.setAddress(person.getAddress());
		responseDTO.setCreatedDate(person.getCreateTimestamp());
		responseDTO.setUpdatedDate(person.getUpdateTimestamp());

		List<RoleDTO> roleDTOs = new ArrayList<>();
		for (PersonRoleEntity personRole : person.getRoles()) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setRoleId(personRole.getRole().getRoleId());
			roleDTO.setRoleName(personRole.getRole().getRoleName());
			roleDTO.setRoleDescription(personRole.getRole().getRoleDescription());
			roleDTOs.add(roleDTO);
		}
		responseDTO.setRoles(roleDTOs);
		return responseDTO;
	}

	private PersonDTO getAdminDetails(PersonEntity person) {

		PersonDTO adminDTO = new PersonDTO();
		adminDTO.setPersonId(person.getPersonId());
		adminDTO.setName(person.getFullName());
		List<RoleDTO> roleDTOs = new ArrayList<>();
		for (PersonRoleEntity personRole : person.getRoles()) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setRoleId(personRole.getRole().getRoleId());
			roleDTO.setRoleName(personRole.getRole().getRoleName());
			roleDTO.setRoleDescription(personRole.getRole().getRoleDescription());
			roleDTOs.add(roleDTO);
		}
		adminDTO.setRoles(roleDTOs);
		return adminDTO;
	}

	private StatusDTO getStatusDetails(SRTicketStatusEntity status) {

		StatusDTO dto = new StatusDTO();
		dto.setStatusCode(status.getStatusCode());
		dto.setStatus(status.getStatusDescription());
		return dto;
	}

	private SRTicketCategoryDTO getCategoryDetails(SRTicketCategoryEntity category) {

		SRTicketCategoryDTO dto = new SRTicketCategoryDTO();
		dto.setCategoryCode(category.getCategoryCode());
		dto.setCategoryName(category.getCategoryName());
		dto.setDescription(category.getDescription());
		return dto;
	}

	private boolean createNewServiceTicket(ServiceTicketDTO ticketDTO) {

		try {
			PersonEntity user = personRepository.findById(ticketDTO.getPersonId())
					.orElseThrow(() -> new RuntimeException("User not found"));
			SRTicketCategoryEntity category = categoryRepository.findById(ticketDTO.getCategory())
					.orElseThrow(() -> new RuntimeException("Category not found"));

			SRTicketsEntity ticket = new SRTicketsEntity();
			ticket.setPerson(user);
			ticket.setCategory(category);
			ticket.setDescription(ticketDTO.getRequestDescription());
			ticket.setCreateTimestamp(Timestamp.from(Instant.now()));
			ticket.setUpdateTimestamp(ticket.getCreateTimestamp()); // Initial update timestamp same as create timestamp
			SRTicketStatusEntity status = statusRepository.findById(SRTicketStatusConstants.IN_PROGRESS)
					.orElseThrow(() -> new RuntimeException("Status not found"));
			ticket.setStatus(status);
			ticket = ticketsRepository.save(ticket);

			saveTicketHistory(ticket);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private boolean updateInProgressTicket(ServiceTicketDTO updateDTO) {

		try {
			SRTicketsEntity ticket = ticketsRepository.findById(updateDTO.getTicketId())
					.orElseThrow(() -> new RuntimeException("Ticket not found"));
			SRTicketCategoryEntity category = categoryRepository.findById(updateDTO.getCategory())
					.orElseThrow(() -> new RuntimeException("Category not found"));
			ticket.setCategory(category);
			ticket.setDescription(updateDTO.getRequestDescription());
			ticket.setUpdateTimestamp(Timestamp.from(Instant.now()));
			ticket = ticketsRepository.save(ticket);

			saveTicketHistory(ticket);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private void saveTicketHistory(SRTicketsEntity ticket) {
		SRTicketHistoryEntity history = new SRTicketHistoryEntity();
		history.setSrTicket(ticket);
		history.setStatusCode(ticket.getStatus());
		history.setUpdateUser(ticket.getAssignedTo());
		history.setUpdateTimestamp(Timestamp.from(Instant.now()));
		historyRepository.save(history);
	}
}
