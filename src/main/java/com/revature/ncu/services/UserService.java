package com.revature.ncu.services;

import com.revature.ncu.util.PasswordUtils;
import com.revature.ncu.util.exceptions.AuthenticationException;
import com.revature.ncu.datasources.documents.AppUser;
import com.revature.ncu.datasources.repositories.UserRepository;
import com.revature.ncu.util.exceptions.ResourcePersistenceException;
import com.revature.ncu.web.dtos.Principal;

import java.time.LocalDateTime;

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

//        if (userRepo.findUserByUsername(newUser.getUsername()) != null) {
//            throw new ResourcePersistenceException("Provided username is already taken!");
//        }
//
//        if (userRepo.findUserByEmail(newUser.getEmail()) != null) {
//            throw new ResourcePersistenceException("Provided username is already taken!");
//        }

        String encryptedPassword = passwordUtils.generateSecurePassword(newUser.getPassword());
        newUser.setPassword(encryptedPassword);

        return userRepo.save(newUser);

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
