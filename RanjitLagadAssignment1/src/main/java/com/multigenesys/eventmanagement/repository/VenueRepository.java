package com.multigenesys.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigenesys.eventmanagement.model.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    boolean existsByNameAndLocation(String name, String location);
}

