package com.multigenesys.eventmanagement.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigenesys.eventmanagement.exception.ResourceNotFoundException;
import com.multigenesys.eventmanagement.model.Venue;
import com.multigenesys.eventmanagement.repository.VenueRepository;
import com.multigenesys.eventmanagement.service.VenueService;


@Service
public class VenueServiceImpl implements VenueService {
	 @Autowired
	    private VenueRepository venueRepository;

	    @Transactional
	    public Venue createVenue(Venue venue) {
	        if (venueRepository.existsByNameAndLocation(venue.getName(), venue.getLocation())) {
	            throw new ValidationException("Venue already exists with the same name and location.");
	        }

	        return venueRepository.save(venue);
	    }

	    public Venue getVenueById(Long id) {
	    	Optional<Venue> optional=venueRepository.findById(id);
	    	if(optional.get()!=null)
	    	{
	    		return optional.get();
	    	}
	    	else
	    	{
	    		throw new ResourceNotFoundException("Venue not found.");
	    	}
	        
	    }

		@Transactional
	    public Venue updateVenue(Long id, Venue venueDetails) {
	        Venue venue = venueRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Venue not Found"));
	        venue.setName(venueDetails.getName());
	        venue.setLocation(venueDetails.getLocation());
	        venue.setCapacity(venueDetails.getCapacity());
	        return venueRepository.save(venue);
	    }

	    @Transactional
	    public void deleteVenue(Long id) {
	        Venue venue = venueRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Venue not Found"));
	        if (venue.getEvent()!=null) {
	            throw new RuntimeException("Cannot delete venue with scheduled events.");
	        }
	        venueRepository.delete(venue);
	    }
}


