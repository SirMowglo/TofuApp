package com.trianaSalesianos.tofuApp.model;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DietDays {
    private UUID id;
    private LocalDateTime day;
    private List<Recipe> recipes;
}
