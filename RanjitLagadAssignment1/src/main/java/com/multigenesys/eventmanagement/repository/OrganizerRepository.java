package com.multigenesys.eventmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigenesys.eventmanagement.model.Organizer;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    
	Optional<Organizer> findByNameAndContactInfo(String name, String contactInfo);
}
