package com.pm.eventservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Event Name is required")
    @Size(max = 500, message = "Event name must be at most 500 characters")
    private String eventName;

    @NotBlank(message = "Event type is required")
    private String eventType;

    @NotNull
    private String address;

    @NotNull
    private LocalDate eventDate;

    @NotBlank(message = "Performers is required")
    private String performers;

    private boolean ticketGenerationStatus = false;

    private int availableSeats;
}
