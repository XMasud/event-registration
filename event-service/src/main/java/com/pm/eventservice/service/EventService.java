package com.pm.eventservice.service;

import com.pm.eventservice.dto.EventRequestDTO;
import com.pm.eventservice.dto.EventResponseDTO;
import com.pm.eventservice.dto.TicketGenerateRequestDTO;
import com.pm.eventservice.exception.NotFoundException;
import com.pm.eventservice.mapper.EventMapper;
import com.pm.eventservice.model.Event;
import com.pm.eventservice.repository.EventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventService(EventRepository eventRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.eventRepository = eventRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<EventResponseDTO> getEvents(){

        List<Event> events = eventRepository.findAll();
        return events.stream().map(EventMapper::toDTO).toList();
    }

    public Optional<Event> findEvent(UUID id){

        Optional<Event> event = eventRepository.findById(id);

        if (event.isEmpty())
            throw new NotFoundException("Event not found with: " + id);

        return event;
    }

    public EventResponseDTO createEvent(EventRequestDTO request){

        Event createEvent = eventRepository.save(EventMapper.toModel(request));
        return EventMapper.toDTO(createEvent);
    }

    public EventResponseDTO updateEvent(UUID id, EventRequestDTO request){

        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event not found with: " + id));

        event.setEventName(request.getEventName());
        event.setEventType(request.getEventType());
        event.setEventDate(request.getEventDate());
        event.setAddress(request.getAddress());
        event.setPerformers(request.getPerformers());
        event.setAvailableSeats(request.getAvailableSeats());

        Event updateEvent = eventRepository.save(event);

        return EventMapper.toDTO(updateEvent);
    }

    public void deleteEvent(UUID id){

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));

        eventRepository.delete(event);
    }

    public int ticketGenerate(UUID id) {

        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event not found with: " + id));

        HashMap<String, Object> message = new HashMap<>();
        message.put("event-id",event.getId().toString());
        message.put("seat-count", event.getAvailableSeats());

        kafkaTemplate.send("seats-opened", message);

        return event.getAvailableSeats();
    }
}
