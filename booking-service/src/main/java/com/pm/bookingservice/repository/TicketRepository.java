package com.pm.bookingservice.repository;

import com.pm.bookingservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    boolean existsByEventId(UUID eventId);
    @Transactional
    void deleteByEventId(UUID eventId);
}
