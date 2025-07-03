package com.pm.event.service;

import com.pm.event.dto.EventRequestDTO;
import com.pm.event.dto.EventResponseDTO;
import com.pm.event.exception.NotFoundException;
import com.pm.event.model.Event;
import com.pm.event.repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventResponseDTO> getEvents(){
        return eventRepository.findAll().stream()
                .map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getEventName(),
                        event.getEventType(),
                        event.getAddress(),
                        event.getEventDate(),
                        event.getPerformers(),
                        event.getAvailableSeats()
                )).collect(Collectors.toList());
    }

    public Optional<Event> findEvent(UUID id){

        Optional<Event> event = eventRepository.findById(id);

        if (event.isEmpty())
            throw new NotFoundException("Event not found with: " + id);

        return event;
    }

    public EventResponseDTO createEvent(EventRequestDTO request){

        Event event = new Event();
        event.setEventName(request.getEventName());
        event.setEventType(request.getEventType());
        event.setEventDate(request.getEventDate());
        event.setAddress(request.getAddress());
        event.setPerformers(request.getPerformers());
        event.setAvailableSeats(request.getAvailableSeats());

        Event saveEvent = eventRepository.save(event);

        return new EventResponseDTO(
                saveEvent.getId(),
                saveEvent.getEventName(),
                saveEvent.getEventType(),
                saveEvent.getAddress(),
                saveEvent.getEventDate(),
                saveEvent.getPerformers(),
                saveEvent.getAvailableSeats()
        );
    }

    public void deleteEvent(UUID id){

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));

        eventRepository.delete(event);
    }
}
