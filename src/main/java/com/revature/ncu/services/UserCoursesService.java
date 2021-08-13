package com.revature.ncu.services;

import com.revature.ncu.datasources.documents.AppUser;
import com.revature.ncu.datasources.documents.UserCourses;
import com.revature.ncu.datasources.repositories.UserCoursesRepository;
import com.revature.ncu.util.exceptions.NotRegisteredForCourseException;

import java.util.List;

// Service for handling most user course list business logic and passing information into the UserCourse repository

public class UserCoursesService {

    private final UserCoursesRepository userCourseListRepo;


    public UserCoursesService(UserCoursesRepository userCourseRepo) {
        this.userCourseListRepo = userCourseRepo;

    }

    // Initialize a user's course list on the database when they register
    public void initialize(){
    }

    // Checks to see if the user has already joined a course, passes the course requested and the username to the Repo if not
    public void joinCourse(String courseToJoin){

    }


    // Compares user's entered course against a list of the user's courses, ignores case.
    // Returns proper case if the course is found.
    public String verifyCourseEntry(List<String> joinedCourses, String userEntry){

        for (String course:joinedCourses)
        {
            if (course.equalsIgnoreCase(userEntry))
                return course;
        }

        System.out.println("You have not registered for a course by this name.");
        throw new NotRegisteredForCourseException("User attempted to withdraw for a course they were not enrolled in.");

    }

    // Verifies if a user is in any courses before attempting to remove the course.
    public void leaveCourse(String courseToLeave) {

    }

    public List<String> getCourses(){
        return null;
    }

    public void updateCourseNameInUserList(String originalName, String newName){

        //TODO Could check if any users have registered for this course?
        userCourseListRepo.updateCourseNameInAllUserLists(originalName, newName);

    }

    public void expungeCourse(String courseName){

        //TODO Could check if any users have registered for this course?
        userCourseListRepo.removeCourseFromAllUserLists(courseName);

    }

}


//TODO Make future methods more granular in their assignments so tests aren't such a headache.
