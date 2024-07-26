package com.example.polusServiceRequest.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SR_TICKET_STATUS")
public class SRTicketStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_CODE")
    private Long statusCode;

    @Column(name = "STATUS_DESCRIPTION", nullable = false)
    private String statusDescription;
    
    @ManyToOne
    @JoinColumn(name = "UPDATE_USER")
    private PersonEntity updateUser;

    @Column(name = "UPDATE_TIMESTAMP")
    private Timestamp updateTimestamp;

}
