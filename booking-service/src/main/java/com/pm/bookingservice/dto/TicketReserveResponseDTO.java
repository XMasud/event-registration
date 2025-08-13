package com.pm.bookingservice.dto;

import com.pm.bookingservice.enums.TicketStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TicketReserveResponseDTO {

    private UUID ticketId;

    private UUID eventId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private LocalDateTime reservedAt;
    private LocalDateTime expiresAt;
}
