package com.pm.eventservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventRequestDTO {

    private String eventName;
    private String eventType;
    private String address;
    private LocalDate eventDate;
    private String performers;
    private int availableSeats;

    public EventRequestDTO(String eventName, String eventType, String address, LocalDate eventDate, String performers, int availableSeats) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.address = address;
        this.eventDate = eventDate;
        this.performers = performers;
        this.availableSeats = availableSeats;
    }
}
