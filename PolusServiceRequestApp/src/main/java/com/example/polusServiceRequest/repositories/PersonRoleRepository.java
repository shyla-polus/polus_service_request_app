package com.example.polusServiceRequest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.polusServiceRequest.models.PersonRoleEntity;

public interface PersonRoleRepository extends JpaRepository<PersonRoleEntity, Long> {}