package com.pm.eventservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDTO {
    private UUID id;
    private String eventName;
    private String eventType;
    private String address;
    private LocalDate eventDate;
    private String performers;
    private int availableSeats;
}
