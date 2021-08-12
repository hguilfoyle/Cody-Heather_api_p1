package com.revature.projectzero.screens;

import com.revature.projectzero.documents.Course;
import com.revature.projectzero.services.CourseService;
import com.revature.projectzero.services.UserCoursesService;
import com.revature.projectzero.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

// Allows faculty to edit existing courses
//TODO A view all courses screen would be useful here...

public class EditCourseScreen extends Screen {
    private final Logger logger = LogManager.getLogger(EditCourseScreen.class);
    private final CourseService courseService;
    private final UserCoursesService userCoursesService;

    public EditCourseScreen(BufferedReader consoleReader, ScreenRouter router, CourseService courseService,
                            UserCoursesService userCoursesService) {
        super("EditCourseScreen", "/edit-course", consoleReader, router);
        this.courseService = courseService;
        this.userCoursesService = userCoursesService;
    }

    @Override
    public void render() throws IOException {

        System.out.println("\nEdit course screen\n\n" +
                "Please select an option:\n" +
                "1) Edit a Course\n" +
                "2) Add Courses\n" +
                "3) Remove Courses\n" +
                "4) Previous Screen\n" +
                "5) Return to Dashboard.\n");

        String userSelection  = consoleReader.readLine();

        switch(userSelection)
        {
            case "1":
                System.out.println("Enter the abbreviation for the course\n" +
                        "You would like to Edit:");
                String markedCourse = consoleReader.readLine();
                try{
                    Course editCourse = courseService.verifyCourse(markedCourse);
                    System.out.println("Which field would you like to edit?\n\n" +
                            "1) Course Name\n" +
                            "2) Course abbreviation\n" +
                            "3) Course description.\n" +
                            "4) Open/Close Course to new students/withdrawal requests\n");

                    String userSelection2 = consoleReader.readLine();
                    switch (userSelection2) {
                        case "1":
                            System.out.println("Enter the new course name.");
                            String newCourseName = consoleReader.readLine();
                            userCoursesService.updateCourseNameInUserList(editCourse.getCourseName(), newCourseName);
                            courseService.updateCourseName(editCourse, newCourseName);
                            break;
                        case "2":
                            System.out.println("Enter the new course abbreviation.");
                            String newCourseAbv = consoleReader.readLine();
                            courseService.updateCourseAbv(editCourse, newCourseAbv);
                            break;
                        case "3":
                            System.out.println("Enter the new course description.");
                            String newCourseDesc = consoleReader.readLine();
                            courseService.updateCourseDesc(editCourse, newCourseDesc);
                            break;
                        case "4":
                            if (editCourse.isOpen()) {
                                System.out.println("Course closed.");
                            } else {
                                System.out.println("Course opened.");
                            }
                            courseService.toggleOpen(editCourse);
                            break;
                        default:
                            System.out.println("Invalid entry, canceling edit process...");
                    }

                }catch (Exception e) {
                    logger.error("Course modification failed. Reason:",e);
                    System.out.println("Course modification process cancelled");
                }
                break;
            case "2":
                router.navigate("/new-course");
                break;
            case "3":
                router.navigate("/remove-course");
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
