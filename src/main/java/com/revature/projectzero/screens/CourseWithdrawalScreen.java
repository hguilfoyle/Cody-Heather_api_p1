package com.revature.projectzero.screens;

import com.revature.projectzero.services.CourseService;
import com.revature.projectzero.services.UserCoursesService;
import com.revature.projectzero.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

// Allows a student to withdraw from a course.

public class CourseWithdrawalScreen extends Screen {

    private final Logger logger = LogManager.getLogger(CourseWithdrawalScreen.class);
    private final CourseService courseService;
    private final UserCoursesService userCoursesService;

    public CourseWithdrawalScreen(BufferedReader consoleReader, ScreenRouter router,
                                  CourseService courseService, UserCoursesService userCoursesService) {
        super("RemoveCourseScreen", "/course-withdrawal", consoleReader, router);
        this.courseService = courseService;
        this.userCoursesService = userCoursesService;
    }

    @Override
    public void render() throws IOException {

        System.out.println("\nCourse Withdrawal Screen.\n\n" +
                "Please Select an Option.\n" +
                    "1) Begin withdrawal process\n" +
                    "2) Previous screen\n" +
                    "3) View registered classes\n" +
                    "4) Return to Dashboard.\n");

            String userSelection = consoleReader.readLine();

            switch(userSelection)
            {
                case "1":
                    try {
                        System.out.println("You have registered for the following courses:");
                        // Escapes if the user has not registered for any courses.
                        List<String> courses= userCoursesService.getCourses();
                        System.out.println(courses);
                        System.out.println("Enter the full name of the course\n" +
                                "you would like to withdraw from: ");
                        String marked = consoleReader.readLine();
                        // Verify the user's entry (not case sensitive) and create a new string holding the name
                        // that matches the case in the database
                        String courseName = userCoursesService.verifyCourseEntry(courses, marked);
                        // Verify that the course is open, escapes if the course is closed.
                        courseService.verifyCourseOpenByName(courseName);
                        System.out.println("If you wish to attend " + courseName + " you must register again before " +
                                "the registration window closes\n" +
                                "Are you sure?\n" +
                                "1) Yes\n" +
                                "2) No\n");

                            String userVerification = consoleReader.readLine();

                            switch (userVerification) {
                                case "1":
                                    System.out.println("Withdrawing from " + courseName + "...");
                                    userCoursesService.leaveCourse(courseName);
                                    System.out.println("Withdrawal successful!");
                                    break;
                                case "2":
                                    System.out.println("Canceling withdrawal process...");
                                    break;
                                default:
                                    System.out.println("Invalid entry, canceling withdrawal process...");
                            }
                    }catch (Exception e) {
                        logger.error("Course registration failed. Reason:", e);
                        System.out.println("Course registration process canceled!");
                    }
                    break;
                case "2":
                    router.goBack();
                    break;
                case "3":
                    router.navigate("/registered-courses");
                    break;
                case "4":
                    router.navigate("/student-home");
                    break;
                default:
                    System.out.println("Invalid entry, please try again.");
            }

    }
}
