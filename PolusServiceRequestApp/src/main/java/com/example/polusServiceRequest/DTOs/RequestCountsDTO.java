package com.example.polusServiceRequest.DTOs;

import lombok.Data;

@Data
public class RequestCountsDTO {

	private Long totalRequests;
	private Long inProgressRequests;
	private Long assignedRequests;
	private Long approvedRequests;
	private Long rejectedRequests;
	private Long assignedToMeRequests;
	private Long adminApproveRequests;
	private Long adminRejectRequests;
	
}
