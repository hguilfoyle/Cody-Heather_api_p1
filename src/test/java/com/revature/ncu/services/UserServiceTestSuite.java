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


//    @Test
//    public void register_returnsSuccessfully_whenGivenValidUser(){
//        //Arrange
//        AppUser expectedResult = new AppUser("test", "test", "te.st@test.test", "test", "test.test",false);
//        AppUser validUser = new AppUser("test", "test", "te.st@test.test", "test", "test.test",false);
//        when(mockValidator.newUserEntryValidator(validUser)).thenReturn(true);
//        when(mockUserRepo.findUserByUsername(anyString())).thenReturn(null);
//        when(mockUserRepo.findUserByEmail(anyString())).thenReturn(null);
//        when(mockUserRepo.save(validUser)).thenReturn(expectedResult);
//
//        //Act
//        AppUser actualResult = sut.register(validUser);
//
//        //Assert
//        Assert.assertEquals(expectedResult,actualResult);
//        verify(mockValidator,times(1)).newUserEntryValidator(validUser);
//        verify(mockUserRepo,times(1)).findUserByUsername(anyString());
//        verify(mockUserRepo,times(1)).findUserByEmail(anyString());
//        verify(mockUserRepo,times(1)).save(validUser);
//
//    }
//
//    @Test(expected = ResourcePersistenceException.class)
//    public void register_throwsException_whenGivenUser_withDuplicateUsername() {
//
//        // Arrange
//        AppUser existingUser = new AppUser("original", "original", "original", "duplicate", "original",false);
//        AppUser duplicate = new AppUser("first", "last", "email", "duplicate", "password",false);
//        when(mockUserRepo.findUserByUsername(duplicate.getUsername())).thenReturn(existingUser);
//
//        // Act
//        try {
//            sut.register(duplicate);
//        } finally {
//            // Assert
//            verify(mockUserRepo, times(1)).findUserByUsername(duplicate.getUsername());
//            verify(mockUserRepo, times(0)).save(duplicate);
//        }
//
//    }
//
//    @Test(expected = ResourcePersistenceException.class)
//    public void register_throwsException_whenGivenUser_withDuplicateEmail() {
//
//        // Arrange
//        AppUser existingUser = new AppUser("original", "original", "duplicate", "original", "original",false);
//        AppUser duplicate = new AppUser("first", "last", "duplicate", "username", "password",false);
//        when(mockUserRepo.findUserByUsername(duplicate.getUsername())).thenReturn(null);
//        when(mockUserRepo.findUserByEmail(duplicate.getEmail())).thenReturn(existingUser);
//
//
//        // Act
//        try {
//            sut.register(duplicate);
//        } finally {
//            // Assert
//            verify(mockUserRepo, times(1)).findUserByUsername(duplicate.getUsername());
//            verify(mockUserRepo, times(0)).save(duplicate);
//        }
//
//    }

//    @Test(expected = InvalidEntryException.class)
//    public void register_throwsException_whenGivenUser_withBlankValues(){
//        //Arrange
//        AppUser invalidUser = new AppUser("","","","","",false);
//        when(mockValidator.newUserEntryValidator(invalidUser)).thenThrow(InvalidEntryException.class);
//
//        //Act
//        try{
//            sut.register(invalidUser);
//        } finally{
//            // Assert
//            verify(mockValidator, times(1)).newUserEntryValidator(invalidUser);
//            verify(mockUserRepo, times(0)).save(invalidUser);
//        }
//
//    }




}
