package com.multigenesys.eventmanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.multigenesys.eventmanagement.model.Organizer;
import com.multigenesys.eventmanagement.service.OrganizerService;

@RestController
@RequestMapping("/organizers")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @PostMapping
    public ResponseEntity<Organizer> createOrganizer(@Valid @RequestBody Organizer organizer) {
    	return new ResponseEntity<>(organizerService.createOrganizer(organizer),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organizer> getOrganizerById(@PathVariable Long id) {
    	return new ResponseEntity<>(organizerService.getOrganizerById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organizer> updateOrganizer(@PathVariable Long id,@Valid @RequestBody Organizer organizer) {
        return new ResponseEntity<>(organizerService.updateOrganizer(id, organizer),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Organizer> deleteOrganizer(@PathVariable Long id) {
        return new ResponseEntity<>(organizerService.deleteOrganizer(id),HttpStatus.OK);
    }
}

