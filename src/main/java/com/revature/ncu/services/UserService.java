package com.revature.ncu.services;

import com.revature.ncu.util.InputValidator;
import com.revature.ncu.util.exceptions.AuthenticationException;
import com.revature.ncu.datasources.documents.AppUser;
import com.revature.ncu.datasources.repositories.UserRepository;
import com.revature.ncu.util.exceptions.ResourcePersistenceException;

// Service for handling most User-related business logic and passing information into the User repository

public class UserService {

    private final UserRepository userRepo;

    private final InputValidator inputValidator;

    // Injecting Dependencies
    public UserService(UserRepository userRepo, InputValidator inputValidator) {
        this.userRepo = userRepo;
        this.inputValidator = inputValidator;
    }

    // Register a new user
    public AppUser register(AppUser newUser) {

        // Throws exception if entry is invalid
        inputValidator.newUserEntryValidator(newUser);

        if (userRepo.findUserByUsername(newUser.getUsername()) != null)
        {
            throw new ResourcePersistenceException("Provided username is already taken!");
        }
        else if (userRepo.findUserByEmail(newUser.getEmail()) != null)
        {
            throw new ResourcePersistenceException("Provided email is already taken!");
        }
        return userRepo.save(newUser);

    }

    // Validate user credentials and log them in
    public AppUser login(String username, String password) {

        AppUser authUser = userRepo.findUserByCredentials(username, password);

        if (authUser == null) {
            throw new AuthenticationException("Invalid credentials provided!");
        }


        return authUser;
    }


}
