package com.revature.ncu.util;
import com.revature.ncu.repositories.CourseRepository;
import com.revature.ncu.repositories.UserCoursesRepository;
import com.revature.ncu.repositories.UserRepository;
import com.revature.ncu.services.CourseService;
import com.revature.ncu.services.UserCoursesService;
import com.revature.ncu.services.UserService;

/*
 * The "heart" of the program.
 * Instantiates all of the screens and injects the dependencies needed to perform operations.
 * Contains static variable and object references used by the Screens.
 *
 */

public class AppState {


    private static boolean appRunning;


    public AppState() {

        // Dependencies for injection
        InputValidator inputValidator = new InputValidator();


        // Create app components and dependencies
        UserSession userSession = new UserSession();
        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo, userSession, inputValidator);

        CourseRepository courseRepo = new CourseRepository();
        CourseService courseService = new CourseService(courseRepo, inputValidator);

        UserCoursesRepository courseListRepo = new UserCoursesRepository();
        UserCoursesService userCoursesService = new UserCoursesService(courseListRepo, userSession);
    }


    public void startup() {

    }

    // Static method for properly shutting down the app
    public static void closeApp() {

    }
}
