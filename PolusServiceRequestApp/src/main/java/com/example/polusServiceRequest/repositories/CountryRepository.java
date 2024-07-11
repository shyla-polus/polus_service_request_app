package com.example.polusServiceRequest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.polusServiceRequest.models.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, String> {
}
