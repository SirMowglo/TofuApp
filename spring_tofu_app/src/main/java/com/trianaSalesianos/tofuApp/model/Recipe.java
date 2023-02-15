package com.trianaSalesianos.tofuApp.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Recipe implements Serializable {
    @Id
    private UUID id;
    private String name;
    private String description;
    private int prepTime;   // in minutes
    private String steps;
    private String img;
    @CreatedDate
    private LocalDateTime createdAt;

    //TODO Gestion de fetch type lazy con subgraphos y seteo por defecto de las listas en vacia
    //TODO Gestion del borrado de los ingredientes y usuario
    // (si se borra receta, no se borra usuario, pero si se borra usuario se borra receta)
    @ToString.Exclude
    @ManyToOne()
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_RECIPE_USER"))
    private User author;
    @ToString.Exclude
    @ManyToMany(mappedBy = "favorites", fetch = FetchType.EAGER)
    private List<User> favoritedBy;

    @Builder.Default
    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();
}
