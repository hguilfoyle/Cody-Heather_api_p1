package com.revature.ncu.services;

import com.revature.ncu.util.PasswordUtils;
import com.revature.ncu.util.exceptions.AuthenticationException;
import com.revature.ncu.datasources.documents.AppUser;
import com.revature.ncu.datasources.repositories.UserRepository;
import com.revature.ncu.util.exceptions.InvalidRequestException;
import com.revature.ncu.util.exceptions.ResourceNotFoundException;
import com.revature.ncu.util.exceptions.ResourcePersistenceException;
import com.revature.ncu.web.dtos.AppUserDTO;
import com.revature.ncu.web.dtos.Principal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Service for handling most User-related business logic and passing information into the User repository

public class UserService {

    private final UserRepository userRepo;
    private final PasswordUtils passwordUtils;

    private final InputValidatorService inputValidatorService;

    // Injecting Dependencies
    public UserService(UserRepository userRepo, InputValidatorService inputValidatorService, PasswordUtils passwordUtils) {
        this.userRepo = userRepo;
        this.inputValidatorService = inputValidatorService;
        this.passwordUtils = passwordUtils;
    }

    // Register a new user
    public AppUser register(AppUser newUser) {

        // Throws exception if entry is invalid
        inputValidatorService.newUserEntryValidator(newUser);

        if (userRepo.findUserByUsername(newUser.getUsername()) != null) {
            throw new ResourcePersistenceException("Provided username is already taken!");
        }

        if (userRepo.findUserByEmail(newUser.getEmail()) != null) {
            throw new ResourcePersistenceException("Provided username is already taken!");
        }

        // Encrypt password for sending to server storage
        String encryptedPassword = passwordUtils.generateSecurePassword(newUser.getPassword());
        newUser.setPassword(encryptedPassword);

        return userRepo.save(newUser);

    }



    //  List<AppUserDTO> list = new ArrayList<>();
    //        for (AppUser appUser : userRepo.findAll()) {
    //            AppUserDTO appUserDTO = new AppUserDTO(appUser);
    //            list.add(appUserDTO);
    //        }
    //        return list;
    //
    // ^^ this is the same thing as the block of code below.
    // Grabs all user objects from the repo, streams it into an iterable list, maps a new AppUserDTO
    // for each AppUser, and collects them all into a list.
    public List<AppUserDTO> findAll() {
        return userRepo.findAll()
                .stream()
                .map(AppUserDTO::new)
                .collect(Collectors.toList());
    }

    public AppUserDTO findUserById(String id) {

        if (id == null || id.trim().isEmpty()) {
            throw new InvalidRequestException("Invalid id provided");
        }

        AppUser user = userRepo.findById(id);

        if (user == null) {
            throw new ResourceNotFoundException();
        }

        return new AppUserDTO(user);

    }

    // Validate user credentials and log them in
    public Principal login(String username, String password) {

        String encryptedPassword = passwordUtils.generateSecurePassword(password);
        AppUser authUser = userRepo.findUserByCredentials(username, encryptedPassword);

        if (authUser == null) {
            throw new AuthenticationException("Invalid credentials provided!");
        }

        return new Principal(authUser);
    }


}
