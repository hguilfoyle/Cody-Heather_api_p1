package com.revature.projectzero.services;

import com.revature.projectzero.documents.AppUser;
import com.revature.projectzero.documents.UserCourses;
import com.revature.projectzero.repositories.UserCoursesRepository;
import com.revature.projectzero.util.UserSession;
import com.revature.projectzero.util.exceptions.AlreadyRegisteredForCourseException;
import com.revature.projectzero.util.exceptions.CourseNotJoinedException;
import com.revature.projectzero.util.exceptions.NoCoursesJoinedException;
import com.revature.projectzero.util.exceptions.NotRegisteredForCourseException;

import java.util.List;

// Service for handling most user course list business logic and passing information into the UserCourse repository

public class UserCoursesService {

    private final UserCoursesRepository userCourseListRepo;
    private final UserSession session;

    public UserCoursesService(UserCoursesRepository userCourseRepo, UserSession session) {
        this.userCourseListRepo = userCourseRepo;
        this.session = session;
    }

    // Initialize a user's course list on the database when they register
    public void initialize(){
        AppUser newUser = session.getCurrentUser();
        UserCourses newUserCourseList = new UserCourses(newUser.getUsername());
        userCourseListRepo.save(newUserCourseList);
    }

    // Checks to see if the user has already joined a course, passes the course requested and the username to the Repo if not
    public void joinCourse(String courseToJoin){

        String un = session.getCurrentUser().getUsername();
        List<String> userCourses = userCourseListRepo.findRegisteredCoursesByUsername(un);

        // No need to check for duplicate courses if the list is null
        if(!userCourses.isEmpty()) {
            // Check list for requested course
            for (String course : userCourses) {
                if (courseToJoin.equals(course))
                {
                    System.out.println("You are already registered for this course!");
                    throw new AlreadyRegisteredForCourseException("You have already registered for this course!");
                }
            }
        }

        userCourseListRepo.joinCourse(courseToJoin, un);
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

        String un = session.getCurrentUser().getUsername();
        List<String> userCourses = userCourseListRepo.findRegisteredCoursesByUsername(un);

        // Should not actually be reached, but keeping for posterity
        if (userCourses.isEmpty())
        {
            System.out.println("You have not registered for any courses!");
            throw new NoCoursesJoinedException("The user has not registered for any courses.");
        }
        if(!userCourses.contains(courseToLeave))
        {
            System.out.println("You have not joined that course!");
            throw new CourseNotJoinedException("User attempted to leave a course they have not joined.");
        }

        userCourseListRepo.removeCourseFromUserList(courseToLeave,un);

    }

    public List<String> getCourses(){

        String un = session.getCurrentUser().getUsername();

        if(userCourseListRepo.findRegisteredCoursesByUsername(un).isEmpty()){
            System.out.println("You have not registered for any courses!");
            throw new NoCoursesJoinedException("User has not registered for any courses.");
        }

        return userCourseListRepo.findRegisteredCoursesByUsername(session.getCurrentUser().getUsername());
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
