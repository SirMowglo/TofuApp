package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.user.*;
import com.trianaSalesianos.tofuApp.security.jwt.JwtProvider;
import com.trianaSalesianos.tofuApp.security.refresh.RefreshToken;
import com.trianaSalesianos.tofuApp.security.refresh.RefreshTokenException;
import com.trianaSalesianos.tofuApp.security.refresh.RefreshTokenRequest;
import com.trianaSalesianos.tofuApp.security.refresh.RefreshTokenService;
import com.trianaSalesianos.tofuApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    //TODO check del logout
    // Edicion de usuarios (changePasswords, changeAvatar, editUser(Email, fullname, birthday, description)
    // Borrado del usuario

    @GetMapping("/user")
    public PageDto<UserResponse> getAll(@RequestParam(value = "search", defaultValue = "") String search,
                                        @PageableDefault(size = 10, page = 0) Pageable pageable){
        return userService.getAllBySearch(search,pageable);
    }

    @GetMapping("/user/{username}")
    public UserResponse getUserByUsername(@PathVariable String username){
        return userService.getByUsername(username);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithUserRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithAdminRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        refreshTokenService.deleteByUser(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JwtUserResponse.of(user, token, refreshToken.getToken()));

    }

    @PutMapping("/user/changepassword")
    public UserResponse changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                                       @AuthenticationPrincipal User loggedUser) {
        return userService.changePassword(changePasswordRequest,loggedUser);
    }
    @PutMapping("/auth/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal User user){
        refreshTokenService.deleteByUser(user);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/user/edit")
    public UserResponse editUser(){
        return null;
    }
    @PutMapping("/user/changeavatar")
    public UserResponse changeAvatar(){
        return null;
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verify)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtProvider.generateToken(user);
                    refreshTokenService.deleteByUser(user);
                    RefreshToken refreshToken2 = refreshTokenService.createRefreshToken(user);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(JwtUserResponse.builder()
                                    .token(token)
                                    .refreshToken(refreshToken2.getToken())
                                    .build());
                })
                .orElseThrow(() -> new RefreshTokenException("Refresh token not found"));

    }
}
