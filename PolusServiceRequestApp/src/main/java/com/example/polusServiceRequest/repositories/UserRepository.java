package com.example.polusServiceRequest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.polusServiceRequest.models.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password")
	UserEntity findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	UserEntity findByUsername(String username);

//	@Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.roles r LEFT JOIN FETCH r.role WHERE u.username = :username")
//	UserEntity findUserByUsernameWithRoles(@Param("username") String username);
	
}
