package com.pm.eventservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class EventResponseDTO {
    private UUID id;
    private String eventName;
    private String eventType;
    private String address;
    private LocalDate eventDate;
    private String performers;
    private int availableSeats;

    public EventResponseDTO(UUID id, String eventName, String eventType, String address, LocalDate eventDate, String performers, int availableSeats) {
        this.id = id;
        this.eventName = eventName;
        this.eventType = eventType;
        this.address = address;
        this.eventDate = eventDate;
        this.performers = performers;
        this.availableSeats = availableSeats;
    }
}
