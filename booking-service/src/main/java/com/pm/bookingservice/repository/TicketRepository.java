package com.pm.bookingservice.repository;

import com.pm.bookingservice.enums.TicketStatus;
import com.pm.bookingservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    boolean existsByEventId(UUID eventId);

    @Transactional
    void deleteByEventId(UUID eventId);

    Optional<Ticket> findByIdAndStatus(UUID ticketId, TicketStatus status);

    List<Ticket> findByStatusAndExpiresAtBefore(TicketStatus ticketStatus, LocalDateTime now);
}
