package com.revature.ncu.services;

import com.revature.ncu.datasources.documents.AppUser;
import com.revature.ncu.datasources.documents.Course;
import com.revature.ncu.util.exceptions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *  Class for validating user input to check for:
 *  Empty values,
 *  Password security, (Minimum of 8 characters)
 *  Username length, (Minimum of 4 characters)
 *  invalid username characters, (no symbols in username)
 *
 */

public class UserValidatorService {

    // User restrictions are defined here.
    private static final int MIN_USERNAME = 4;
    private static final int MIN_PASSWORD = 8;

    // Pattern for username verification, can be any character from a-z, A-Z, and 0-9. No symbols permitted.
    static Pattern usernamePattern = Pattern.compile("[^a-zA-Z0-9]");

    // Pattern for simple email verification.
    static Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");

    // Validator for student registration
    public boolean newUserEntryValidator(AppUser user){

        // Matcher for checking that the username matches the given pattern
        Matcher userMatch = usernamePattern.matcher(user.getUsername());
        // Matcher for checking that the email matches the given pattern
        Matcher emailMatch = emailPattern.matcher(user.getEmail());

        if(user.getUsername().trim().equals("")||user.getPassword().trim().equals("")||user.getFirstName().trim().equals("")
                ||user.getLastName().trim().equals("")||user.getEmail().trim().equals(""))
        {
            System.out.println("Fields cannot be blank!");
            throw new InvalidEntryException("Blank fields detected.");
        }
        else if(user.getUsername().length() < MIN_USERNAME)
        {
            System.out.println("Username must be at least 4 characters.");
            throw new InvalidUsernameException("Entered username below the minimum character limit.");
        }
        else if(userMatch.find())
        {
            System.out.println("Username contains invalid characters.");
            throw  new InvalidUsernameException("Invalid characters entered in username.");
        }
        else if(user.getPassword().length() < MIN_PASSWORD)
        {
            System.out.println("Password must be at least 8 characters.");
            throw new InvalidPasswordException("Entered password below the minimum character limit.");
        }
        else if(!emailMatch.find())
        {
            System.out.println("Please enter a valid email address.");
            throw new InvalidEmailException("Invalid email address entered.");
        }

        return true;
    }

}
