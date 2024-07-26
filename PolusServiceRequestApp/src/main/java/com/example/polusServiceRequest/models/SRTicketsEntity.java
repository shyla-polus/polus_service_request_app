package com.example.polusServiceRequest.models;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "SR_TICKETS")
public class SRTicketsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SR_TICKET_ID")
	private Long srTicketId;

	@ManyToOne
	@JoinColumn(name = "PERSON_ID")
	private PersonEntity person;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_CODE")
	private SRTicketCategoryEntity category;

	@ManyToOne
	@JoinColumn(name = "ASSIGNED_TO")
	private PersonEntity assignedTo;

	@ManyToOne
	@JoinColumn(name = "STATUS_CODE")
	private SRTicketStatusEntity status;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATE_TIMESTAMP")
	private Timestamp createTimestamp;

	@Column(name = "UPDATE_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp updateTimestamp;
	
	 @OneToMany(mappedBy = "srTicket", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<SRTicketHistoryEntity> history;

	
}