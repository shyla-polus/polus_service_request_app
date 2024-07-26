package com.example.polusServiceRequest.DTOs;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentDetailsDTO {

	private Long commentId;
	private String Comment;
	private PersonDTO commentUser;
	private Timestamp commentTimestamp;
}
