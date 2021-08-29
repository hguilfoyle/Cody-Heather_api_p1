package com.revature.ncu.services;

import com.revature.ncu.datasources.documents.AppUser;
import com.revature.ncu.datasources.repositories.UserRepository;
import com.revature.ncu.util.PasswordUtils;
import com.revature.ncu.util.exceptions.AuthenticationException;
import com.revature.ncu.util.exceptions.InvalidEntryException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.mockito.Mockito.*;

public class UserServiceTestSuite {

    // Instantiate system under test
    private UserService sut;

    // Mock dependencies required for the system under test
    private UserRepository mockUserRepo;
    private UserValidatorService mockValidator;
    private PasswordUtils mockPasswordUtils;

    // Initialize objects before testing
    @Before
    public void setup(){
        mockUserRepo = mock(UserRepository.class);
        mockValidator = mock(UserValidatorService.class);
        mockPasswordUtils = mock(PasswordUtils.class);
    }

    // Clear out objects after testing by setting them to null
    @After
    public void cleanUp(){
        mockUserRepo = null;
        mockValidator = null;
        mockPasswordUtils = null;
        sut = null;
    }


    // register tests
    @Test
    public void register_executesSuccessfully_whenGivenValidUser() {
        //Arrange
        AppUser newUser = new AppUser("valid","valid","valid@valid.com","valid","valid",false);
        when(mockValidator.newUserEntryValidator(newUser)).thenReturn(true);
        when(mockUserRepo.findUserByUsername(newUser.getUsername())).thenReturn(null);
        when(mockUserRepo.findUserByEmail(newUser.getEmail())).thenReturn(null);

        String passwordCrypt = mockPasswordUtils.generateSecurePassword(newUser.getPassword());
        //Act
        AppUser result = sut.register(newUser);

        //Assert
        verify(mockValidator, times(1)).newUserEntryValidator(newUser);
        verify(mockUserRepo, times(1)).findUserByUsername(anyString());
        verify(mockUserRepo, times(1)).findUserByEmail(anyString());
    }


    // findAll tests


    // findUserById tests


    // login tests


    // getProfNameById tests






}
