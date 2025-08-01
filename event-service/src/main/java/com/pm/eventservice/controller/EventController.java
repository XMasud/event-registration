package com.pm.eventservice.controller;

import com.pm.eventservice.dto.EventRequestDTO;
import com.pm.eventservice.dto.EventResponseDTO;
import com.pm.eventservice.model.Event;
import com.pm.eventservice.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponseDTO> getEvents() {
        return eventService.getEvents();
    }

    @GetMapping("/{id}")
    public Optional<Event> getEventById(@PathVariable UUID id) {
        return eventService.findEvent(id);
    }

    @PostMapping
    public EventResponseDTO saveUser(@RequestBody @Valid EventRequestDTO request) {
        EventResponseDTO response = eventService.createEvent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED).getBody();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
