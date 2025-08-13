package com.pm.bookingservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class BookingConsumer {

    Logger logger = LoggerFactory.getLogger(BookingConsumer.class);

    private final TicketService ticketService;

    public BookingConsumer(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @KafkaListener(topics = "seats-opened", groupId = "booking-service")
    public void consume(Map<String, Object> eventData) {

        try{
            UUID eventId = UUID.fromString((String) eventData.get("event-id"));
            Integer seatCount = (Integer) eventData.get("seat-count");

            ticketService.generateTickets(eventId, seatCount);
            logger.info("📩 Received: {}",eventId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
