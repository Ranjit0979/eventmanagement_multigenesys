package com.multigenesys.eventmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.multigenesys.eventmanagement.model.event.dto.EventCreationDTO;
import com.multigenesys.eventmanagement.model.event.dto.EventDTO;
import com.multigenesys.eventmanagement.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventCreationDTO eventDTO) {
        return new ResponseEntity<>(eventService.createEvent(eventDTO),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return new ResponseEntity<>(eventService.getAllEvents(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
    	return new ResponseEntity<>(eventService.getEventById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id,@RequestBody EventCreationDTO event) {
        return new ResponseEntity<>(eventService.updateEvent(id, event),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventDTO> deleteEvent(@PathVariable Long id) {
        return new ResponseEntity<>(eventService.deleteEvent(id),HttpStatus.OK);
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<EventDTO> getEventsByVenue(@PathVariable Long venueId) {
        return new ResponseEntity<>(eventService.getEventsByVenue(venueId),HttpStatus.OK);
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<EventDTO>> getEventsByOrganizer(@PathVariable Long organizerId) {
    	return new ResponseEntity<>(eventService.getEventsByOrganizer(organizerId),HttpStatus.OK);
    }
    
}


