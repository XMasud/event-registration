package com.pm.eventservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDTO {
    @NotNull
    private String eventName;
    private String eventType;
    private String address;
    private LocalDate eventDate;
    private String performers;
    private int availableSeats;
}
