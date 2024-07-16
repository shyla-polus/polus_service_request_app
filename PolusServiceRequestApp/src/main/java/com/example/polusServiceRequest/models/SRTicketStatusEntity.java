package com.example.polusServiceRequest.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SR_TICKET_STATUS")
public class SRTicketStatusEntity {

    @Id
    @Column(name = "STATUS_CODE")
    private String statusCode;

    @Column(name = "STATUS_DESCRIPTION", nullable = false)
    private String statusDescription;

}
