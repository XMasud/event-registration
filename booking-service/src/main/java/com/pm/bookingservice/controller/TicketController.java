package com.pm.bookingservice.controller;

import com.pm.bookingservice.model.Ticket;
import com.pm.bookingservice.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<Ticket> getEvents() {
        return ticketService.getTickets();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTicket(@PathVariable UUID id){

        ticketService.deleteTicket(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message","Event deleted"));
    }
}
