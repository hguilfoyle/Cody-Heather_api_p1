package com.revature.projectzero;


import com.revature.projectzero.util.AppState;

/*
 * Console-Based Student Management Console for Project 0
 *      Written by Cody McDonald,
 *      An associate at Revature.
 *
 * Faculty Members may:
 *     - Add new courses to the registration catalog
 *     - Edit a course name, abbreviation, or detail
 *     - Remove a course from the registration catalog
 *
 * Students may:
 *     - Register a new account
 *     - Login with existing credentials
 *     - View courses available for registration
 *     - Register for an open and available course
 *     - Cancel registration for a course (if it is open)
 *     - View the course(es) they have registered for
 *
 *
 */



public class CourseManagementApp {

    // Main method creates an AppState object and uses it to run the startup method to build the project.
    public static void main(String[] args) {

        AppState app = new AppState();
        app.startup();

    }

}