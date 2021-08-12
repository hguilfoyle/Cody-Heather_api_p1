package com.revature.projectzero.screens;

import com.revature.projectzero.documents.Course;
import com.revature.projectzero.services.CourseService;
import com.revature.projectzero.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

// Prints out "Available" courses, or courses that are marked as open on the database.

public class ViewCoursesScreen extends Screen {

    private final Logger logger = LogManager.getLogger(ViewCoursesScreen.class);

    private final CourseService courseService;


    public ViewCoursesScreen(BufferedReader consoleReader, ScreenRouter router, CourseService courseService) {
        super("ViewCoursesScreen", "/courses", consoleReader, router);
        this.courseService = courseService;
    }

    @Override
    public void render() throws IOException {

        try {
            System.out.println("The currently available courses are:\n\n");
            for (Course a : courseService.getCourses()) {
                System.out.println("Course name: " + a.getCourseName() + "\n"
                        + "Course Abbreviation: " + a.getCourseAbbreviation() + "\n"
                        + "Course Details: " + a.getCourseDetail() + "\n");

            }
        }catch(Exception e){
            logger.error("Failed to propagate courses to view screen. Reason:");
            logger.error(e.getMessage());
        }
        router.goBack();
    }
}
