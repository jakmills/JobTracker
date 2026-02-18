package com.jake.jobtrack.model;

import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* Core Job Fields */
    private String title;
    private String company;
    private String location;

    @Column(length = 2000)
    private String description;

    private String link;

    @Column(length = 2000)
    private String notes;

    /* Status Tracking */
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    /* Dates */
    private LocalDate appliedDate;
    private Instant firstResponseAt;
    private Instant firstInterviewAt;
    private Instant offerAt;
    private Instant rejectionAt;
    private Instant lastStatusChangedAt;

    /* Table Relationships */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
