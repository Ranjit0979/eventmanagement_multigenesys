package com.multigenesys.eventmanagement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Organizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Organizer name is mandatory")
    private String name;

    @NotBlank(message = "Contact info is mandatory")
    private String contactInfo;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
    private Set<Event> events;
}

