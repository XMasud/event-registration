package com.pm.bookingservice.service;

import com.pm.bookingservice.dto.TicketReserveResponseDTO;
import com.pm.bookingservice.dto.TicketResponseDTO;
import com.pm.bookingservice.enums.TicketStatus;
import com.pm.bookingservice.model.Ticket;
import com.pm.bookingservice.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    Logger logger = LoggerFactory.getLogger(TicketService.class);

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<TicketResponseDTO> getTickets(){

        List<Ticket> tickets =  ticketRepository.findAll();

        return tickets.stream().map(ticket -> new TicketResponseDTO(
                ticket.getId(),
                ticket.getStatus(),
                ticket.getPrice()
        )).collect(Collectors.toList());
    }

    @Transactional
    public void generateTickets(UUID eventId, int count) {

        if (ticketRepository.existsByEventId(eventId)) {
            throw new IllegalStateException("Tickets already generated for event: " + eventId);
        }

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tickets.add(new Ticket(eventId, TicketStatus.AVAILABLE, 0));
        }
        ticketRepository.saveAll(tickets);
    }

    public void deleteTicket(UUID id) {

        boolean existingTicket = ticketRepository.existsByEventId(id);

        if(!existingTicket)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");

        ticketRepository.deleteByEventId(id);
    }

    @Transactional
    public TicketReserveResponseDTO reserveTicket(UUID ticketId) {

        Optional<Ticket> ticketOpt = ticketRepository.findByIdAndStatus(ticketId, TicketStatus.AVAILABLE);

        if(ticketOpt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corresponding ticket not found or available for reservation");

        Ticket ticket = ticketOpt.get();
        ticket.setStatus(TicketStatus.RESERVED);
        ticket.setReservedAt(LocalDateTime.now());
        ticket.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        ticketRepository.save(ticket);

        return new TicketReserveResponseDTO(
                ticket.getId(),
                ticket.getEventId(),
                ticket.getStatus(),
                ticket.getReservedAt(),
                ticket.getExpiresAt()
        );
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredReservations() {

        logger.info("📩 Fixed scheduled ");

        List<Ticket> expiredTickets = ticketRepository
                .findByStatusAndExpiresAtBefore(TicketStatus.RESERVED, LocalDateTime.now());
        for (Ticket ticket : expiredTickets) {
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticket.setReservedAt(null);
            ticket.setExpiresAt(null);
            ticketRepository.save(ticket);
        }
        logger.info("📩 Fixed scheduled ");
    }
}
