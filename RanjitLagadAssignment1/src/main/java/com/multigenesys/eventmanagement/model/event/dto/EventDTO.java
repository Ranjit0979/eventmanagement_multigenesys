package com.multigenesys.eventmanagement.model.event.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EventDTO {
	private Long id;
	private String eventName;
    private LocalDate eventDate;
    private String description;
    private EventVenueDTO venue;
    private EventOrganizerDTO organizer;

}
