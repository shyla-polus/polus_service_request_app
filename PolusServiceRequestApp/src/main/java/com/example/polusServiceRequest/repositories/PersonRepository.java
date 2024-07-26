package com.example.polusServiceRequest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.polusServiceRequest.models.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

	PersonEntity findByUserNameAndPassword(String userName, String password);

	PersonEntity findByUserName(String username);

	@Query("SELECT p FROM PersonEntity p JOIN p.roles r WHERE r.role.roleName = 'APPLICATION_ADMINISTRATOR'")
	List<PersonEntity> findAllApplicationAdministrators();

	@Query("SELECT DISTINCT p FROM PersonEntity p " + "JOIN p.roles pr1 " + "JOIN pr1.role r1 "
			+ "WHERE r1.roleName = 'PRINCIPAL_INVESTIGATOR' " + "AND NOT EXISTS ( "
			+ "    SELECT pr2 FROM PersonRoleEntity pr2 " + "    JOIN pr2.role r2 " + "    WHERE pr2.person = p "
			+ "    AND r2.roleName = 'APPLICATION_ADMINISTRATOR' " + ")")
	List<PersonEntity> findPrincipalInvestigatorsNotAdmins();

//	@Query("SELECT p FROM PersonEntity p JOIN p.roles r WHERE p.personId = :personId AND r.role.roleName = :roleName")
//	PersonEntity findByPersonIdAndRoleName(@Param("personId") Long personId, @Param("roleName") Long roleId);

}
