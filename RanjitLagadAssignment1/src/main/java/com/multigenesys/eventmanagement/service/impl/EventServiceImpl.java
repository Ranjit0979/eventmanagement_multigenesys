package com.multigenesys.eventmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigenesys.eventmanagement.exception.ResourceNotFoundException;
import com.multigenesys.eventmanagement.model.Event;
import com.multigenesys.eventmanagement.model.Organizer;
import com.multigenesys.eventmanagement.model.Venue;
import com.multigenesys.eventmanagement.model.event.dto.EventCreationDTO;
import com.multigenesys.eventmanagement.model.event.dto.EventDTO;
import com.multigenesys.eventmanagement.model.event.dto.EventOrganizerDTO;
import com.multigenesys.eventmanagement.model.event.dto.EventVenueDTO;
import com.multigenesys.eventmanagement.repository.EventRepository;
import com.multigenesys.eventmanagement.repository.OrganizerRepository;
import com.multigenesys.eventmanagement.repository.VenueRepository;
import com.multigenesys.eventmanagement.service.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Transactional
    public EventDTO createEvent(EventCreationDTO eventDTO) {
    	Event event=new Event();
    	event.setEventName(eventDTO.getEventName());
    	event.setEventDate(eventDTO.getEventDate());
    	event.setDescription(eventDTO.getDescription());
    	if (eventDTO.getVenue() != null) {
        	Optional<Venue> optional =venueRepository.findById(eventDTO.getVenue());
    		if(optional.isPresent()) {
    			Venue venue=optional.get();
    			if(venue.getEvent()!=null)
    			{
    				throw new ResourceNotFoundException("Event already scheduled at the venue.");
    			}
    			event.setVenue(venue);	
    		}
    		else
    		{
    			throw new ResourceNotFoundException("Venue does not exist.");
    		}

        }
        else
        {
        	throw new ResourceNotFoundException("Venue is mandatory.");
        }

        if (eventDTO.getOrganizer() != null) {
        	Optional<Organizer> optional2 =organizerRepository.findById(eventDTO.getOrganizer());
    		if(optional2.isPresent()) {
    			event.setOrganizer(optional2.get());
    		}
    		else
    		{
    			throw new ResourceNotFoundException("Organizer does not exist.");
    		}

        }
        else
        {
        	throw new ResourceNotFoundException("Organizer is mandatory.");
        }
        return this.convertEventToEventDTO(this.saveEvent(event));
    }
    
    private Event saveEvent(@Valid Event event)
    {
//    	if (eventRepository.existsByEventDateAndVenue(event.getEventDate(), event.getVenue())) {
//            throw new ResourceNotFoundException("Event already scheduled for this date at the venue.");
//        }
    	return eventRepository.save(event);
    }

    public List<EventDTO> getAllEvents() {
    	List<Event> eventList=eventRepository.findAll();
    	List<EventDTO> eventDTOList=new ArrayList<>();
    	
    	for(Event event:eventList)
    	{
    		eventDTOList.add(this.convertEventToEventDTO(event));
    	}
    	
        return eventDTOList;
    }

    

	public EventDTO getEventById(Long id) {
		Event event=eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found."));
        return this.convertEventToEventDTO(event);
    }

	@Transactional
	public EventDTO updateEvent(Long id, EventCreationDTO eventDetails) {
	    try {
	        Event event = eventRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Event not found."));

	        event.setEventName(eventDetails.getEventName());
	        event.setEventDate(eventDetails.getEventDate());
	        event.setDescription(eventDetails.getDescription());

	        if (eventDetails.getVenue() != null) {
	            Venue newVenue = venueRepository.findById(eventDetails.getVenue())
	                    .orElseThrow(() -> new ResourceNotFoundException("Venue does not exist."));

	            if (event.getVenue() != null && !event.getVenue().getId().equals(newVenue.getId())) {
	                event.getVenue().setEvent(null);
	            }

	            newVenue.setEvent(event);
	            event.setVenue(newVenue);
	        }

	        if (eventDetails.getOrganizer() != null) {
	            Organizer newOrganizer = organizerRepository.findById(eventDetails.getOrganizer())
	                    .orElseThrow(() -> new ResourceNotFoundException("Organizer does not exist."));
	            event.setOrganizer(newOrganizer);
	        }

	        return this.convertEventToEventDTO(eventRepository.save(event));
	    } catch (Exception e) {
	        // Log the exception and throw a custom exception if needed
	        e.printStackTrace(); // Or use a logger
	        throw new RuntimeException("Error updating event", e);
	    }
	}


	@Transactional
	public EventDTO deleteEvent(Long id) {
	    Event event = eventRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Event not found."));
	    
	    EventDTO deletedEvent=this.convertEventToEventDTO(event);
	    
	    // Detach the event from the associated venue
	    Venue venue = event.getVenue();
	    if (venue != null) {
	        venue.setEvent(null);  // Break the association from the venue side
	        venueRepository.save(venue);
	    }

	    // Detach the event from the associated organizer
	    Organizer organizer = event.getOrganizer();
	    if (organizer != null) {
	        organizer.getEvents().remove(event);  // Remove event from organizer's list
	        event.setOrganizer(null);  // Break the association from the event side
	        organizerRepository.save(organizer);
	    }

	    // Finally, delete the event
	    eventRepository.delete(event);
	    
	    return deletedEvent;
	    
	    
	}


    public EventDTO getEventsByVenue(Long venueId) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        System.out.println("============================= "+venue.getId());
        Event event=eventRepository.findByVenue(venue);
        if(event!=null)
        {
        	return this.convertEventToEventDTO(event);
        }
        
        throw new ResourceNotFoundException("Event not found.");
        
    }

    public List<EventDTO> getEventsByOrganizer(Long organizerId) {
        Organizer organizer = organizerRepository.findById(organizerId).orElseThrow(() -> new ResourceNotFoundException("Organizer not found"));
        List<Event> eventList=eventRepository.findByOrganizer(organizer);
    	List<EventDTO> eventDTOList=new ArrayList<>();
    	
    	for(Event event:eventList)
    	{
    		eventDTOList.add(this.convertEventToEventDTO(event));
    	}
    	
        return eventDTOList;
        
    }
    
    private EventDTO convertEventToEventDTO(Event event) {
		EventDTO eventDto=new EventDTO();
		eventDto.setId(event.getId());
		eventDto.setEventName(event.getEventName());
		eventDto.setDescription(event.getDescription());
		eventDto.setEventDate(event.getEventDate());
		eventDto.setVenue(event.getVenue()!=null?
				                                this.convertVenueToEventVenueDTO(event.getVenue())
				                                :null);
		eventDto.setOrganizer(event.getOrganizer()!=null?
                this.convertOrganizerToEventOrganizerDTO(event.getOrganizer())
                :null);
		return eventDto;
	}

	private EventOrganizerDTO convertOrganizerToEventOrganizerDTO( Organizer organizer) {
		EventOrganizerDTO eventOrganizerDTO=new EventOrganizerDTO();
		eventOrganizerDTO.setId(organizer.getId());
		eventOrganizerDTO.setName(organizer.getName());
		eventOrganizerDTO.setContactInfo(organizer.getContactInfo());
		return eventOrganizerDTO;
	}

	private EventVenueDTO convertVenueToEventVenueDTO(Venue venue) {
		EventVenueDTO eventVenueDto=new EventVenueDTO();
		eventVenueDto.setName(venue.getName());
		eventVenueDto.setId(venue.getId());
		eventVenueDto.setLocation(venue.getLocation());
		eventVenueDto.setCapacity(venue.getCapacity());
		return eventVenueDto;
	}
}


