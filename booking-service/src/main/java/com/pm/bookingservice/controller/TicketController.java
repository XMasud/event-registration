package com.pm.bookingservice.controller;

import com.pm.bookingservice.dto.TicketReserveResponseDTO;
import com.pm.bookingservice.dto.TicketResponseDTO;
import com.pm.bookingservice.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<TicketResponseDTO> getTicket() {
        return ticketService.getTickets();
    }

    @GetMapping("/reserve/{ticketId}")
    public TicketReserveResponseDTO reserveTicket(@PathVariable UUID ticketId){
        return ticketService.reserveTicket(ticketId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable UUID id){

        ticketService.deleteTicket(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message","Event deleted"));
    }
}
