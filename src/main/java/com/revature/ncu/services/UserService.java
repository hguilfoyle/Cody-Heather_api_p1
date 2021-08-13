package com.revature.ncu.services;

import com.revature.ncu.util.exceptions.AuthenticationException;
import com.revature.ncu.datasources.documents.AppUser;
import com.revature.ncu.datasources.repositories.UserRepository;
import com.revature.ncu.util.exceptions.ResourcePersistenceException;
import com.revature.ncu.web.dtos.Principal;

// Service for handling most User-related business logic and passing information into the User repository

public class UserService {

    private final UserRepository userRepo;

    private final InputValidatorService inputValidatorService;

    // Injecting Dependencies
    public UserService(UserRepository userRepo, InputValidatorService inputValidatorService) {
        this.userRepo = userRepo;
        this.inputValidatorService = inputValidatorService;
    }

    // Register a new user
    public AppUser register(AppUser newUser) {

        // Throws exception if entry is invalid
        inputValidatorService.newUserEntryValidator(newUser);

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
    public Principal login(String username, String password) {

        AppUser authUser = userRepo.findUserByCredentials(username, password);

        if (authUser == null) {
            throw new AuthenticationException("Invalid credentials provided!");
        }

        return new Principal(authUser);
    }


}
