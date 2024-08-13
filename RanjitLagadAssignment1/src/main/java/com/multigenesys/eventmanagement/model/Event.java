package com.multigenesys.eventmanagement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event name is mandatory")
    private String eventName;

    @FutureOrPresent(message = "Event date must be in the present or future")
    @NotNull(message = "Event date is mandatory")
    private LocalDate eventDate;

    private String description;

    @OneToOne
    @NotNull(message = "Venue is mandatory")
    @JsonIgnore
    private Venue venue;

    @ManyToOne
    @NotNull(message = "Organizer is mandatory")
    @JsonIgnore
    private Organizer organizer;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}



