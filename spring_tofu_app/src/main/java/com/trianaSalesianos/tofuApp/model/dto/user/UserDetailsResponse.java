package com.trianaSalesianos.tofuApp.model.dto.user;

import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsResponse {
    private String username, avatar, fullname, email, description, birthday;

    private List<RecipeResponse> recipes;
    private List<RecipeResponse> favorites;

    public static UserDetailsResponse fromUser(User user){
        return UserDetailsResponse.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .description(user.getDescription())
                .birthday(user.getBirthday().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .recipes(user.getRecipes()
                        .stream()
                        .map(RecipeResponse::fromRecipe)
                        .collect(Collectors.toList()))
                .favorites(user.getFavorites()
                        .stream()
                        .map(RecipeResponse::fromRecipe)
                        .collect(Collectors.toList()))
                .build();
    }
}
