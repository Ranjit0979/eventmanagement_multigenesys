package com.multigenesys.eventmanagement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Venue name is mandatory")
    private String name;

    @NotBlank(message = "Venue location is mandatory")
    private String location;

    private Integer capacity;

    @OneToOne(mappedBy = "venue", cascade = CascadeType.ALL)
    private Event event;
}
