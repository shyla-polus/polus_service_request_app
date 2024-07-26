package com.example.polusServiceRequest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.polusServiceRequest.models.SRTicketsEntity;

public interface SRTicketsRepository extends JpaRepository<SRTicketsEntity, Long> {

	@Query(value = "SELECT * FROM sr_tickets t WHERE t.person_id = :personId AND t.status_code = :statusCode ORDER BY t.update_timestamp DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<SRTicketsEntity> findServiceTicketsByPersonId(@Param("personId") Long personId,
			@Param("statusCode") Long statusCode, @Param("limit") Long limit, @Param("offset") Long offset);

	@Query(value = "SELECT COUNT(*) FROM SR_TICKETS WHERE PERSON_ID = :personId", nativeQuery = true)
	long countAllRequestsByPersonId(@Param("personId") Long personId);

//	@Query(value = "SELECT COUNT(*) FROM SR_TICKETS WHERE PERSON_ID = :personId AND STATUS_CODE = 1", nativeQuery = true)
//	long countInProgressRequestsByPersonId(@Param("personId") Long personId);
//
//	@Query(value = "SELECT COUNT(*) FROM SR_TICKETS WHERE PERSON_ID = :personId AND STATUS_CODE = 2", nativeQuery = true)
//	long countAssignedRequestsByPersonId(@Param("personId") Long personId);
//
//	@Query(value = "SELECT COUNT(*) FROM SR_TICKETS WHERE PERSON_ID = :personId AND STATUS_CODE = 3", nativeQuery = true)
//	long countApprovedRequestsByPersonId(@Param("personId") Long personId);
//
//	@Query(value = "SELECT COUNT(*) FROM SR_TICKETS WHERE PERSON_ID = :personId AND STATUS_CODE = 4", nativeQuery = true)
//	long countRejectedRequestsByPersonId(@Param("personId") Long personId);

	@Query(value = "SELECT * FROM SR_TICKETS WHERE PERSON_ID = :personId", nativeQuery = true)
	List<SRTicketsEntity> findAllRequestsByPersonId(@Param("personId") Long personId);

	@Query(value = "SELECT COUNT(*) FROM SR_TICKETS WHERE PERSON_ID = :personId AND STATUS_CODE = :statusCode", nativeQuery = true)
	long requestsCount(@Param("personId") Long personId, @Param("statusCode") Long statusCode);

	@Query(value = "SELECT * FROM SR_TICKETS WHERE ASSIGNED_TO = :adminId AND STATUS_CODE = :statusCode ORDER BY UPDATE_TIMESTAMP DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<SRTicketsEntity> findAllAssignedToMeTickets(@Param("adminId") Long adminId,
	        @Param("statusCode") Long statusCode, @Param("limit") Long limit, @Param("offset") Long offset);
	
	@Query(value = "SELECT COUNT(*) FROM SR_TICKETS WHERE ASSIGNED_TO = :adminId AND STATUS_CODE = :statusCode", nativeQuery = true)
	Long countAssignedToMeTickets(@Param("adminId") Long adminId, @Param("statusCode") Long statusCode);

}
