package com.revature.projectzero.screens;

import com.revature.projectzero.documents.AppUser;
import com.revature.projectzero.documents.UserCourses;
import com.revature.projectzero.services.UserCoursesService;
import com.revature.projectzero.services.UserService;
import com.revature.projectzero.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.BufferedReader;
import java.io.IOException;


// Allows a student to create a new account. Logs in and creates a session automatically if successful.

public class NewStudentScreen extends Screen {

    // Instantiate Logger and dependencies.
    private final Logger logger = LogManager.getLogger(NewStudentScreen.class);
    private final UserService userService;
    private final UserCoursesService courseListService;

    // Inject dependencies required for this screen.
    public NewStudentScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService,
                            UserCoursesService userCoursesService) {
        super("NewStudentScreen", "/new-student", consoleReader, router);
        this.userService = userService;
        this.courseListService = userCoursesService;
    }

    @Override
    public void render() throws IOException {
        System.out.println("\nNew Student Registration...\n");

        System.out.print("First name: ");
        String firstName = consoleReader.readLine();

        System.out.print("Last name: ");
        String lastName = consoleReader.readLine();

        System.out.print("\nEmail\n*Must be a valid email address: ");
        String email = consoleReader.readLine();

        System.out.print("\nUsername:\n*Must be between 4 and 25 characters: ");
        String username = consoleReader.readLine();

        System.out.print("\nPassword:\n*Must be a minimum of 8 characters. ");
        String password = consoleReader.readLine();

        try{
            // Create an object that holds the new user's input. UserType is false for students.
            AppUser newUser = new AppUser(firstName, lastName, email, username, password, false);

            // Instantiate the new user's course list.


            // Validate the user's entry and add them to the database along with their course list.
            userService.register(newUser);
            courseListService.initialize();

            // Inform the user of the success and log it.
            System.out.println("Student registered!");
            logger.info("New student registered and added to database.");
            router.navigate("/student-home");
        }catch (Exception e) {
            System.out.println("User not registered!\n");
            logger.error("User registration failed. Reason: ", e);
            router.navigate("/welcome");
        }


    }
}
