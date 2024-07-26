package com.example.polusServiceRequest.DTOs;

import lombok.Data;

@Data
public class MakeOrRemoveAdminDTO {

	private Long personId;
	private Long role;
	private Long adminID;
}
