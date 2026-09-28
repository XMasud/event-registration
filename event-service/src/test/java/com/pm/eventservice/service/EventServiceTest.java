package com.pm.eventservice.service;

import com.pm.eventservice.dto.EventRequestDTO;
import com.pm.eventservice.dto.EventResponseDTO;
import com.pm.eventservice.exception.NotFoundException;
import com.pm.eventservice.model.Event;
import com.pm.eventservice.repository.EventRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void shouldReturnEventList() {

        Event event1 = Event.builder()
                .id(UUID.randomUUID())
                .eventName("Fall Concert 26")
                .eventType("Concert")
                .address("Kaiserslautern, RLP")
                .eventDate(LocalDate.of(2026, 10, 1))
                .performers("Taylor Swift")
                .availableSeats(5000)
                .build();

        Event event2 = Event.builder()
                .id(UUID.randomUUID())
                .eventName("Tech Conference 2026")
                .eventType("Conference")
                .address("Berlin, Germany")
                .eventDate(LocalDate.of(2026, 11, 15))
                .performers("Various Speakers")
                .availableSeats(300)
                .build();

        when(eventRepository.findAll()).thenReturn(List.of(event1, event2));

        List<EventResponseDTO> eventList = eventService.getEvents();

        assertNotNull(eventList);
        assertEquals(2, eventList.size());
        assertEquals("Fall Concert 26", eventList.get(0).getEventName());
        assertEquals("Tech Conference 2026", eventList.get(1).getEventName());

        verify(eventRepository).findAll();
    }

    @Test
    void shouldFindEvent() {

        UUID id = UUID.randomUUID();

        Event event = Event.builder()
                .id(id)
                .eventName("Tech Conference 2026")
                .eventType("Conference")
                .address("Berlin, Germany")
                .eventDate(LocalDate.of(2026, 11, 15))
                .performers("Various Speakers")
                .availableSeats(300)
                .build();

        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        Optional<Event> result = eventService.findEvent(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Tech Conference 2026", result.get().getEventName());

        verify(eventRepository).findById(id);
    }

    @Test
    void createEvent() {
    }

    @Test
    void updateEvent() {
    }

    @Test
    void deleteEvent() {
    }

    @Test
    void getProduct() {
    }
}