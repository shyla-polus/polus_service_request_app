package com.example.polusServiceRequest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.polusServiceRequest.models.UserRoleEntity;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {}