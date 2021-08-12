package com.revature.projectzero.screens;

import com.revature.projectzero.documents.AppUser;
import com.revature.projectzero.util.exceptions.AuthenticationException;
import com.revature.projectzero.services.UserService;
import com.revature.projectzero.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

// Verifies a user's credentials and logs them in

public class LoginScreen extends Screen {

    private final Logger logger = LogManager.getLogger(LoginScreen.class);
    private final UserService userService;

    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("LoginScreen", "/login", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws IOException {
        String un;
        String pw;

        System.out.println("\nUser Login Screen\n" +
                "Please enter your username:");

        un  = consoleReader.readLine();

        System.out.println("Please enter your password:");

        pw = consoleReader.readLine();

        System.out.println("Validating....");

        try {
            AppUser authUser = userService.login(un, pw);
            System.out.println("Login successful!");

            if(authUser.isFaculty())
                router.navigate("/faculty-home");
            else
                router.navigate("/student-home");

        } catch (AuthenticationException ae) {
            System.out.println("No user found with provided credentials!");
            System.out.println("Navigating back to welcome screen...");
            logger.error("User login failed. Reason: ", ae);
            System.out.println("User login failed!");
            router.navigate("/welcome");
        }
    }
}
