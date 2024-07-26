package com.example.polusServiceRequest.DTOs;

import java.util.List;

import lombok.Data;

@Data
public class PersonDTO {

	private Long personId;
	private String name;
	private List<RoleDTO> roles;
}
