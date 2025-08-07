package com.pm.eventservice.mapper;

import com.pm.eventservice.dto.EventRequestDTO;
import com.pm.eventservice.dto.EventResponseDTO;
import com.pm.eventservice.model.Event;

public class EventMapper {

    public static EventResponseDTO toDTO(Event request) {

        return new EventResponseDTO(
                request.getId(),
                request.getEventName(),
                request.getEventType(),
                request.getAddress(),
                request.getEventDate(),
                request.getPerformers(),
                request.getAvailableSeats()
        );
    }

    public static Event toModel(EventRequestDTO request) {

        Event event = new Event();

        event.setEventName(request.getEventName());
        event.setEventType(request.getEventType());
        event.setEventDate(request.getEventDate());
        event.setAddress(request.getAddress());
        event.setPerformers(request.getPerformers());
        event.setAvailableSeats(request.getAvailableSeats());

        return event;
    }
}
