package com.pm.bookingservice.repository;

import com.pm.bookingservice.enums.TicketStatus;
import com.pm.bookingservice.model.Ticket;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    boolean findByIdAndStatus(UUID ticketId, TicketStatus status);

    List<Ticket> findByStatusAndExpiresAtBefore(TicketStatus ticketStatus, LocalDateTime now);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Ticket t WHERE t.id = :id AND t.status = 'AVAILABLE'")
    Optional<Ticket> lockAvailableForConfirm(@Param("id") UUID id);
}
