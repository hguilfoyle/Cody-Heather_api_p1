package com.revature.projectzero.screens;

import com.revature.projectzero.documents.Course;
import com.revature.projectzero.services.CourseService;
import com.revature.projectzero.services.UserCoursesService;
import com.revature.projectzero.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

// Allows a student to register for a course

public class CourseRegistrationScreen extends Screen {

    private final Logger logger = LogManager.getLogger(CourseRegistrationScreen.class);
    private final CourseService courseService;
    private final UserCoursesService userCoursesService;

    public CourseRegistrationScreen(BufferedReader consoleReader, ScreenRouter router, CourseService courseService,
                                    UserCoursesService userCoursesService) {
        super("CourseRegistrationScreen", "/join-course", consoleReader, router);
        this.courseService = courseService;
        this.userCoursesService = userCoursesService;
    }

    @Override
    public void render() throws IOException {

        System.out.println("\nCourse Registration Screen:\n\n" +
                "1) Register for a course\n" +
                "2) View Available Courses\n" +
                "3) Cancel Course Registration\n" +
                "4) View Registered Courses\n" +
                "5) Go back\n" +
                "6) Return to dashboard\n");

        String userSelection  = consoleReader.readLine();
        switch (userSelection)
        {
            case "1":
                System.out.print("Enter the Abbreviation for the course you would like to join: ");
                String joining = consoleReader.readLine();
                System.out.println("Applying for course...");
                try {
                    Course joiningCourse = courseService.verifyCourseOpenByAbbreviation(joining);
                    userCoursesService.joinCourse(joiningCourse.getCourseName());
                    System.out.println("Course application accepted!");
                }catch (Exception e) {
                    logger.error("User failed to join course. Reason:",e);
                    System.out.println("Course registration process canceled.");
                }
                break;
            case "2":
                router.navigate("/courses");
                break;
            case "3":
                router.navigate("/course-withdrawal");
                break;
            case "4":
                router.navigate("/registered-courses");
                break;
            case "5":
                router.goBack();
                break;
            case "6":
                router.navigate("/student-home");
                break;
            default:
                System.out.println("Invalid entry.");
        }


    }
}
