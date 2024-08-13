package com.multigenesys.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigenesys.eventmanagement.model.Event;
import com.multigenesys.eventmanagement.model.Organizer;
import com.multigenesys.eventmanagement.model.Venue;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByVenue(Venue venue);
    List<Event> findByOrganizer(Organizer organizer);
    boolean existsByEventDateAndVenue(LocalDate eventDate, Venue venue);
}

