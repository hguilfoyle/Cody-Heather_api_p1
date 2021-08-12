package com.revature.projectzero.screens;

import com.revature.projectzero.util.ScreenRouter;

import java.io.*;

import static com.revature.projectzero.util.AppState.closeApp;

// Welcomes the user and provides options to login, create a new student account, or close the program.
public class WelcomeScreen extends Screen {


    public WelcomeScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("WelcomeScreen", "/welcome", consoleReader, router);
    }

    @Override
    public void render() throws IOException {


        // Output welcome message and display options.
        System.out.println("Welcome to the Student Management Console.\n" +
                "1) Login\n" +
                "2) New Student\n" +
                "3) Exit application\n" +
                "> ...\n");


        String userSelection  = consoleReader.readLine();
        switch (userSelection)
        {
            case "1":
                router.navigate("/login");
                break;
            case "2":
                router.navigate("/new-student");
                break;
            case "3":
                System.out.println("Exiting application...");
                closeApp();
                break;
            default:
                System.out.println("Invalid entry. Please try again.");
        }


        }



    }




