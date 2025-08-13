package com.pm.bookingservice.service;

import com.pm.bookingservice.dto.TicketResponseDTO;
import com.pm.bookingservice.enums.TicketStatus;
import com.pm.bookingservice.model.Ticket;
import com.pm.bookingservice.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

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
}
