package com.trianaSalesianos.tofuApp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Recipe {
    //TODO modelado de las recetas
    @Id
    private UUID id;
    private String name;
    private String description;
    private int prepTime;   // in minutes
    private List<String> steps;
    private String img;
    private LocalDateTime createdAt;
}
