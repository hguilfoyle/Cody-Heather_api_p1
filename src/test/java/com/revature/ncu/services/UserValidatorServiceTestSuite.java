package com.revature.ncu.services;

import com.revature.ncu.datasources.documents.AppUser;
import com.revature.ncu.datasources.documents.Course;
import com.revature.ncu.util.exceptions.*;

import org.junit.*;


public class UserValidatorServiceTestSuite {

    private UserValidatorService sut;

    @Before
    public void setup(){
        sut = new UserValidatorService();
    }

    @After
    public void cleanUp(){
        sut = null;
    }


    // User Entry Validator Tests

    @Test
    public void userEntryValidator_returnsTrue_whenGivenValidUser(){
        // Arrange
        AppUser validUser = new AppUser("valid","valid","valid@email.com","valid5","valid-password",false);
        // Act
        sut.newUserEntryValidator(validUser);
        // Assert
        Assert.assertTrue(sut.newUserEntryValidator(validUser));

    }


    @Test(expected = InvalidEntryException.class)
    public void userEntryValidator_throwsException_whenGivenBlankValues(){
        // Arrange
        AppUser invalidUser = new AppUser("","","","","",false);

        // Act
        sut.newUserEntryValidator(invalidUser);

    }

    @Test(expected = InvalidUsernameException.class)
    public void userEntryValidator_throwsException_whenGivenInvalidUsernameUnderFourChars(){
        // Arrange
        AppUser invalidUser = new AppUser("valid","valid","valid@email.com","inv","valid-password",false);

        //Act
        sut.newUserEntryValidator(invalidUser);
    }

    @Test(expected = InvalidUsernameException.class)
    public void userEntryValidator_throwsException_whenGivenInvalidUsernameWithSymbols(){
        // Arrange
        AppUser invalidUser = new AppUser("valid","valid","valid@email.com","invalid!","valid-password",false);

        //Act
        sut.newUserEntryValidator(invalidUser);
    }

    @Test(expected = InvalidEmailException.class)
    public void userEntryValidator_throwsException_whenGivenInvalidEmail(){
        // Arrange
        AppUser invalidUser = new AppUser("valid","valid","invalid-email.com","valid","valid-password",false);
        // Act
        sut.newUserEntryValidator(invalidUser);
    }

    @Test(expected = InvalidPasswordException.class)
    public void userEntryValidator_throwsException_whenGivenInvalidPassword(){
        // Arrange
        AppUser invalidUser = new AppUser("valid","valid","valid@email.com","valid","invalid",false);
        // Act
        sut.newUserEntryValidator(invalidUser);
    }

}




