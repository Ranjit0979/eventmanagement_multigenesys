package com.multigenesys.eventmanagement.service;


import com.multigenesys.eventmanagement.model.Organizer;


public interface OrganizerService {
    Organizer createOrganizer(Organizer organizer);
    Organizer getOrganizerById(Long id);
    Organizer updateOrganizer(Long id, Organizer organizerDetails);
    Organizer deleteOrganizer(Long id);
}

