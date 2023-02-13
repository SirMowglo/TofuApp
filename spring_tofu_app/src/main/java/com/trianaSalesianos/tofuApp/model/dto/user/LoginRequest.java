package com.trianaSalesianos.tofuApp.model.dto.user;

import com.trianaSalesianos.tofuApp.validation.annotation.StrongPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class LoginRequest {
    private String username;
    @StrongPassword
    private String password;
}
