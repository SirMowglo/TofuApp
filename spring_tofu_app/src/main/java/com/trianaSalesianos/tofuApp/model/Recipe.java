package com.trianaSalesianos.tofuApp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Recipe {
    @Id
    UUID id;
    String description;
    int prepTime;   // in minutes
    List<String> steps;
    String img;
    LocalDate createdAt;
}
