package com.multigenesys.eventmanagement.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigenesys.eventmanagement.exception.ResourceNotFoundException;
import com.multigenesys.eventmanagement.model.Organizer;
import com.multigenesys.eventmanagement.repository.OrganizerRepository;
import com.multigenesys.eventmanagement.service.OrganizerService;

@Service
public class OrganizerServiceImpl implements OrganizerService {
	 @Autowired
	    private OrganizerRepository organizerRepository;

	    @Transactional
	    public Organizer createOrganizer(Organizer organizer) {
	        Optional<Organizer> existingOrganizer = organizerRepository.findByNameAndContactInfo(organizer.getName(), organizer.getContactInfo());
	        if (existingOrganizer.isPresent()) {
	            throw new RuntimeException("Organizer already exists with the same name and contact information.");
	        }
	        return organizerRepository.save(organizer);
	    }

	    public Organizer getOrganizerById(Long id) {
	        return organizerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Organizer not found."));
	    }

	    @Transactional
	    public Organizer updateOrganizer(Long id, Organizer organizerDetails) {
	        Organizer organizer = getOrganizerById(id);
	        organizer.setName(organizerDetails.getName());
	        organizer.setContactInfo(organizerDetails.getContactInfo());
	        return organizerRepository.save(organizer);
	    }

	    @Transactional
	    public Organizer deleteOrganizer(Long id) {
	        Organizer organizer = getOrganizerById(id);
	        if (!organizer.getEvents().isEmpty()) {
	            throw new RuntimeException("Cannot delete organizer with scheduled events.");
	        }
	        organizerRepository.delete(organizer);
	        return organizer;
	    }
}


