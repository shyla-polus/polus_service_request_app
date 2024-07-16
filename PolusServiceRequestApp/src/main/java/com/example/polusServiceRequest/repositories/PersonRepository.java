package com.example.polusServiceRequest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.polusServiceRequest.models.PersonEntity;


@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

	
	 PersonEntity findByUserNameAndPassword(String userName, String password);

    PersonEntity findByUserName(String username);
    
    @Query("SELECT p FROM PersonEntity p JOIN p.roles r WHERE r.role.roleName = 'APPLICATION_ADMINISTRATOR'")
    List<PersonEntity> findAllApplicationAdministrators();
}


