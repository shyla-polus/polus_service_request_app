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
@Table(name = "SR_TICKET_COMMENTS")
public class SRTicketCommentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "SR_TICKET_ID", referencedColumnName = "SR_TICKET_ID")
    private SRTicketsEntity srTicket;

    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "COMMENT_USER")
    private PersonEntity commentUser;

    @Column(name = "COMMENT_TIMESTAMP", nullable = false)
    private Timestamp commentTimestamp;
}
