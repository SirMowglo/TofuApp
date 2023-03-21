package com.trianaSalesianos.tofuApp.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trianaSalesianos.tofuApp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewAvatarResponse {

    protected String username, avatar, fullname;


    public static NewAvatarResponse fromUser(User user) {

        return NewAvatarResponse.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullname(user.getFullname())
                .build();
    }
}
