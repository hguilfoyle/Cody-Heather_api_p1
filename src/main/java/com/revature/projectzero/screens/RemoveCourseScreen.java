package com.revature.projectzero.screens;

import com.revature.projectzero.documents.Course;
import com.revature.projectzero.services.CourseService;
import com.revature.projectzero.services.UserCoursesService;
import com.revature.projectzero.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;


// Allows faculty members to remove a class from the database.

public class RemoveCourseScreen extends Screen {
    private final Logger logger = LogManager.getLogger(RemoveCourseScreen.class);
    private final CourseService courseService;
    private final UserCoursesService userCoursesService;

    public RemoveCourseScreen(BufferedReader consoleReader, ScreenRouter router, CourseService courseService, UserCoursesService userCoursesService) {
        super("RemoveCourseScreen", "/remove-course", consoleReader, router);
        this.courseService = courseService;
        this.userCoursesService = userCoursesService;
    }

    @Override
    public void render() throws IOException {

         System.out.println("\nCourse Removal Screen\n\n" +
                "Please select an option:\n" +
                "1) Begin removal process\n" +
                "2) Add Courses\n" +
                "3) Edit Courses\n" +
                "4) Previous Screen\n" +
                "5) Return to Dashboard.\n");

        String userSelection  = consoleReader.readLine();

        switch(userSelection)
        {
            case "1":
                System.out.println("Enter the abbreviation for the course\n" +
                        "You would like to remove:");
                String markedCourse = consoleReader.readLine();
                try {
                    Course removingCourse = courseService.verifyCourse(markedCourse);

                    System.out.println("This action cannot be undone, are you sure?\n" +
                            "1) Yes\n" +
                            "2) No\n");

                    String userVerification = consoleReader.readLine();

                    switch (userVerification) {
                        case "1":
                            System.out.println("Removing class...");
                            userCoursesService.expungeCourse(removingCourse.getCourseName());
                            courseService.removeCourse(removingCourse);
                            break;
                        case "2":
                            System.out.println("Canceling deletion process...");
                            break;
                        default:
                            System.out.println("Invalid entry, canceling deletion process...");

                    }

                }catch(Exception e) {
                    logger.error("Course deletion failed. Reason:", e);
                    System.out.println("Aborting course deletion process.");
                }
                break;
            case "2":
                router.navigate("/new-course");
                break;
            case "3":
                router.navigate("/edit-course");
                break;
            case "4":
                router.goBack();
                break;
            case "5":
                router.navigate("/faculty-home");
                break;
            default:
                System.out.println("Invalid entry. Please try again.");
        }

    }
}
