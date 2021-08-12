package com.revature.projectzero.screens;

import com.revature.projectzero.services.UserCoursesService;
import com.revature.projectzero.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

// Allows a student to view their registered courses.

public class RegisteredCoursesScreen extends Screen {

    private final Logger logger = LogManager.getLogger(RegisteredCoursesScreen.class);
    private final UserCoursesService userCoursesService;

    public RegisteredCoursesScreen(BufferedReader consoleReader, ScreenRouter router, UserCoursesService userCoursesService) {
        super("RegisteredCoursesScreen", "/registered-courses", consoleReader, router);
        this.userCoursesService = userCoursesService;
    }

    @Override
    public void render() throws IOException {
        try {
            System.out.println("You have registered for the following courses:\n");
            System.out.println(userCoursesService.getCourses().toString());
        }catch (Exception e) {
            logger.error("Failed to retrieve user courses. Reason:\n",e);
        }
        System.out.println("Please select an option.\n" +
                "1) Register for a course\n" +
                "2) Withdraw from a course\n" +
                "3) Go back\n" +
                "4) Return to dashboard\n");

        String userSelection  = consoleReader.readLine();

        switch (userSelection)
        {
            case "1":
                router.navigate("/join-course");
                break;
            case "2":
                router.navigate("/course-withdrawal");
                break;
            case "3":
                router.goBack();
                break;
            case "4":
                router.navigate("/student-home");
                break;
            default:
                System.out.println("Invalid entry. Please try again.");
        }

    }
}
