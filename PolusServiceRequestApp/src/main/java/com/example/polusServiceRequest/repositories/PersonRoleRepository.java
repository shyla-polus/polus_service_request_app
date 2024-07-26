package com.example.polusServiceRequest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.polusServiceRequest.models.PersonRoleEntity;

import jakarta.transaction.Transactional;

public interface PersonRoleRepository extends JpaRepository<PersonRoleEntity, Long> {

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM PERSON_ROLE WHERE PERSON_ID = :userId AND ROLE_ID = :roleId", nativeQuery = true)
	void removeAdminPrivilege(@Param("userId") Long userId, @Param("roleId") Long roleId);

	@Query(value = "SELECT PERSON_ROLE_ID FROM PERSON_ROLE WHERE PERSON_ID = :personId AND ROLE_ID = :roleId", nativeQuery = true)
	Long findPersonRoleIdByPersonIdAndRoleId(@Param("personId") Long personId, @Param("roleId") Long roleId);
}