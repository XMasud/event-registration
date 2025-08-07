package com.pm.bookingservice.model;

import com.pm.bookingservice.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID eventId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    public Ticket(UUID eventId, TicketStatus status) {
        this.eventId = eventId;
        this.status = status;
    }
}
