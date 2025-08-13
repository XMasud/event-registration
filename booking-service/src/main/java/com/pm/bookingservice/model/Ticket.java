package com.pm.bookingservice.model;

import com.pm.bookingservice.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID eventId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private int price;

    private LocalDateTime reservedAt;
    private LocalDateTime expiresAt;

    public Ticket(UUID eventId, TicketStatus status, int price) {
        this.eventId = eventId;
        this.status = status;
        this.price = price;
    }

    @PrePersist
    public void generateRandomPrice() {
        if (this.price == 0) {
            this.price = (int) (30 + (Math.random() * 170));
        }
    }
}
