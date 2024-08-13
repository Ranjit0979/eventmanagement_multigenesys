package com.multigenesys.eventmanagement.model.event.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EventCreationDTO {
	private String eventName;
    private LocalDate eventDate;
    private String description;
    private Long venue;
    private Long organizer;

}
