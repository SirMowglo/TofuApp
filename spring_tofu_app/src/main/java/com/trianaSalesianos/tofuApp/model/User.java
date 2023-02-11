package com.trianaSalesianos.tofuApp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class User {
    @Id
    UUID id;

    String username;
    String password;
    String email;
    LocalDate birthday;
    LocalDate createdAt;

}
