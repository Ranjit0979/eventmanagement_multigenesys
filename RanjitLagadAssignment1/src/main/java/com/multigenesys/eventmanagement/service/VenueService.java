package com.multigenesys.eventmanagement.service;

import com.multigenesys.eventmanagement.model.Venue;


public interface VenueService {
    Venue createVenue(Venue venue);
    Venue getVenueById(Long id);
    Venue updateVenue(Long id, Venue venueDetails);
    void deleteVenue(Long id);
}

