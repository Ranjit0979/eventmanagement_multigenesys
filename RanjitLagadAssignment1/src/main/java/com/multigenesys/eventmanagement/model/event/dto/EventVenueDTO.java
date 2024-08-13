package com.multigenesys.eventmanagement.model.event.dto;

import lombok.Data;

@Data
public class EventVenueDTO {
	private Long id;

    private String name;

    private String location;

    private Integer capacity;

}
