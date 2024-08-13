package com.multigenesys.eventmanagement.service;

import com.multigenesys.eventmanagement.model.event.dto.EventCreationDTO;
import com.multigenesys.eventmanagement.model.event.dto.EventDTO;

import java.util.List;

public interface EventService {
	EventDTO createEvent(EventCreationDTO event);
    List<EventDTO> getAllEvents();
    EventDTO getEventById(Long id);
    EventDTO updateEvent(Long id, EventCreationDTO eventDetails);
    EventDTO deleteEvent(Long id);
    EventDTO getEventsByVenue(Long venueId);
    List<EventDTO> getEventsByOrganizer(Long organizerId);
}

