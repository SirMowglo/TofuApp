package com.trianaSalesianos.tofuApp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Ingredient {
    //TODO Modelado de los ingredientes
    @Id
    private UUID id;
    private String name;
    private String img;
}
