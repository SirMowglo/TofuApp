package com.trianaSalesianos.tofuApp.service;

import com.trianaSalesianos.tofuApp.exception.PwDataErrorException;
import com.trianaSalesianos.tofuApp.exception.UserNotFoundException;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.UserRole;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.user.*;
import com.trianaSalesianos.tofuApp.repository.UserRepository;
import com.trianaSalesianos.tofuApp.search.spec.GenericSpecificationBuilder;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteria;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteriaExtractor;
import com.trianaSalesianos.tofuApp.service.files.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final StorageService storageService;

    public User createUser(CreateUserRequest createUserRequest, EnumSet<UserRole> roles) {
        User user =  User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .fullname(createUserRequest.getFullname())
                .email(createUserRequest.getEmail())
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public User createUserWithUserRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, EnumSet.of(UserRole.USER));
    }

    public User createUserWithAdminRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, EnumSet.of(UserRole.ADMIN));
    }
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    public Optional<User> edit(User user) {
        return userRepository.findById(user.getId())
                .map(u -> {
                    u.setAvatar(user.getAvatar());
                    u.setFullname(user.getFullname());
                    return userRepository.save(u);
                }).or(() -> Optional.empty());

    }

    public Optional<User> editPassword(UUID userId, String newPassword) {

        // Aqu?? no se realizan comprobaciones de seguridad. Tan solo se modifica

        return userRepository.findById(userId)
                .map(u -> {
                    u.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(u);
                }).or(() -> Optional.empty());

    }

    public void delete(User user) {
        deleteById(user.getId());
    }

    public void deleteById(UUID id) {
        // Prevenimos errores al intentar borrar algo que no existe
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
    }

    public boolean passwordMatch(User user, String clearPassword) {
        return passwordEncoder.matches(clearPassword, user.getPassword());
    }

    public UserResponse changePassword(ChangePasswordRequest changePasswordRequest, User loggedUser){
        //TODO comprobar que funciona la nueva implementacion
        /*try {
            if (passwordMatch(loggedUser, changePasswordRequest.getOldPassword())) {
                Optional<User> modified = editPassword(loggedUser.getId(), changePasswordRequest.getNewPassword());
                if (modified.isPresent())
                    return UserResponse.fromUser(modified.get());
            }else{
                // Lo ideal es que esto se gestionara de forma centralizada
                // Se puede ver c??mo hacerlo en la formaci??n sobre Validaci??n con Spring Boot
                // y la formaci??n sobre Gesti??n de Errores con Spring Boot
                throw new RuntimeException();
            }
        }catch (RuntimeException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Data Error");
        }*/

        if (passwordMatch(loggedUser, changePasswordRequest.getOldPassword())) {
            Optional<User> modified = editPassword(loggedUser.getId(), changePasswordRequest.getNewPassword());
            if (modified.isPresent())
                return UserResponse.fromUser(modified.get());
        }else{
            throw new PwDataErrorException();
        }
        return null;
    }

    public PageDto<UserResponse> search(List<SearchCriteria> params, Pageable pageable){
        GenericSpecificationBuilder<User> userSpecificationBuilder = new GenericSpecificationBuilder<>(params, User.class);

        Specification<User> spec = userSpecificationBuilder.build();
        Page<UserResponse> userResponsePage = userRepository.findAll(spec,pageable).map(UserResponse::fromUser);

        return new PageDto<>(userResponsePage);
    }

    public PageDto<UserResponse> getAllBySearch(String search, Pageable pageable){
        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        PageDto<UserResponse> res = search(params,pageable);

        if (res.getContent().isEmpty()) throw new UserNotFoundException();

        return res;
    }

    public UserDetailsResponse getByUsername(String username){
        User user= userRepository.findFirstByUsername(username)
                .orElseThrow(() ->  new UserNotFoundException());

        return UserDetailsResponse.fromUser(user);
    }

    @Transactional
    public NewAvatarResponse changeAvatar(MultipartFile file, User user){
        String filename = storageService.store(file);
        user.setAvatar(filename);

        NewAvatarResponse newAvatar = NewAvatarResponse.fromUser(userRepository.save(user));
        return newAvatar;
    }

    public UserResponse editUser(User user, EditUserRequest editUserRequest){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (!editUserRequest.getEmail().isEmpty())
            user.setEmail(editUserRequest.getEmail());

        if(!editUserRequest.getBirthday().isEmpty()){
            LocalDate newBirthday = LocalDate.parse(editUserRequest.getBirthday(), formatter);
            user.setBirthday(newBirthday);
        }
        if(!editUserRequest.getFullname().isEmpty())
            user.setFullname(editUserRequest.getFullname());

        if(!editUserRequest.getDescription().isEmpty())
            user.setDescription(editUserRequest.getDescription());

        return UserResponse.fromUser(userRepository.save(user));
    }

    public UserResponse editUser(String username, EditUserRequest editUserRequest){
        User user = userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (!editUserRequest.getEmail().isEmpty())
            user.setEmail(editUserRequest.getEmail());

        if(!editUserRequest.getBirthday().isEmpty()){
            LocalDate newBirthday = LocalDate.parse(editUserRequest.getBirthday(), formatter);
            user.setBirthday(newBirthday);
        }
        if(!editUserRequest.getFullname().isEmpty())
            user.setFullname(editUserRequest.getFullname());

        if(!editUserRequest.getDescription().isEmpty())
            user.setDescription(editUserRequest.getDescription());

        return UserResponse.fromUser(userRepository.save(user));
    }
}
