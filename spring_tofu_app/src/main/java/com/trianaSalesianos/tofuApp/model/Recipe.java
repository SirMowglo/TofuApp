package com.trianaSalesianos.tofuApp.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private UUID id;
    private String name;
    private String description;

    @Builder.Default
    private Integer prepTime = 0;   // in minutes
    private String steps;
    @Builder.Default
    private String img = "https://ichef.bbci.co.uk/food/ic/food_16x9_832/recipes/british_shakshuka_26737_16x9.jpg";
    private String category;  //Vegetariano, vegano, hiper-proteico, hiper-calorico, hipo-calorico...
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    //TODO Gestion de fetch type lazy con subgraphos y seteo por defecto de las listas en vacia
    //TODO Gestion del borrado de los ingredientes y usuario
    // (si se borra receta, no se borra usuario, pero si se borra usuario se borra receta)
    @ToString.Exclude
    @ManyToOne()
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_RECIPE_USER"))
    private User author;
    @ToString.Exclude
    @Builder.Default
    @ManyToMany(mappedBy = "favorites", fetch = FetchType.EAGER)
    private List<User> favoritedBy = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();
}
