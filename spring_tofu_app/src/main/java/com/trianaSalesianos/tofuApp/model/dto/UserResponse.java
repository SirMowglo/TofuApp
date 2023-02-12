package com.trianaSalesianos.tofuApp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trianaSalesianos.tofuApp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String id;
    private String username, avatar, fullName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;


    public static UserResponse fromUser(User user) {

        return UserResponse.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
