package com.revature.projectzero.screens;

import com.revature.projectzero.documents.AppUser;
import com.revature.projectzero.services.UserService;
import com.revature.projectzero.util.ScreenRouter;

import java.io.BufferedReader;
import java.io.IOException;

// Home screen for faculty users.

public class FacultyDashboardScreen extends Screen{

    private final UserService userService;

    public FacultyDashboardScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("FacultyHomeScreen", "/faculty-home", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws IOException {

        AppUser currentUser = userService.getSession().getCurrentUser();


        System.out.println("Faculty Screen\n\n" +
                "Welcome, " + currentUser.getFirstName()+ ".\n" +
                "Please select an option.\n" +
                "1) Add a new course.\n" +
                "2) Edit a course.\n" +
                "3) Remove a course.\n" +
                "4) Log out.\n" +
                "> ...\n");

        String userSelection  = consoleReader.readLine();

        switch (userSelection)
        {
            case "1":
                router.navigate("/new-course");
                break;
            case "2":
                router.navigate("/edit-course");
                break;
            case "3":
                router.navigate("/remove-course");
                break;
            case "4":
                System.out.println("Logging out...");
                userService.getSession().closeSession();
                router.navigate("/welcome");
                router.deleteHistory();
                break;
            default:
                System.out.println("Invalid entry. Please try again.");
        }

    }
}
