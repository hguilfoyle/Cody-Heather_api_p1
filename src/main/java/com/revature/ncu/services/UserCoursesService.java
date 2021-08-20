package com.revature.ncu.services;

import com.revature.ncu.datasources.documents.Course;
import com.revature.ncu.datasources.documents.UserCourses;
import com.revature.ncu.datasources.repositories.CourseRepository;
import com.revature.ncu.datasources.repositories.UserCoursesRepository;
import com.revature.ncu.util.exceptions.AlreadyRegisteredForCourseException;
import com.revature.ncu.util.exceptions.CourseNotOpenException;
import com.revature.ncu.util.exceptions.NoSuchCourseException;
import com.revature.ncu.util.exceptions.NotRegisteredForCourseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// Service for handling most user course list business logic and passing information into the UserCourse repository

public class UserCoursesService {

    private final UserCoursesRepository userCourseListRepo;
    private final CourseValidatorService courseValidatorService;
    private final CourseRepository courseRepository;

    private final Logger logger = LoggerFactory.getLogger(UserCoursesService.class);



    public UserCoursesService(UserCoursesRepository userCourseRepo, CourseValidatorService cVS, CourseRepository courseRepo) {
        this.userCourseListRepo = userCourseRepo;
        this.courseValidatorService = cVS;
        this.courseRepository = courseRepo;

    }

    // Initialize a user's course list on the database when they register
    public void initialize(String username){
        UserCourses newUserCourseList = new UserCourses(username);
        userCourseListRepo.save(newUserCourseList);
    }

    // Checks to see if the user has already joined a course, passes the course requested and the username to the Repo if not
    public void joinCourse(String joiningCourseAbv, String username){

        Course course = courseRepository.findCourseByAbbreviation(joiningCourseAbv);

        if(course==null){

            throw new NoSuchCourseException("No such course found!");
        }

        // Making sure course is open
        if(!courseValidatorService.isOpen(course)) {
            logger.info("User tried to register for course that was closed");
            throw new CourseNotOpenException("Course is closed!");
        }

        // Making sure user is not already registered
        for(String id : course.getStudentUsernames()) {
            if(id.equals(username)) {
                logger.info("User tried to join a course they were already registered for");
                throw new AlreadyRegisteredForCourseException("User already registered for this course!");
            }
        }

        userCourseListRepo.joinCourse(joiningCourseAbv,username);
        courseRepository.addStudentUsername(joiningCourseAbv, username);
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

    // Verifies if a user is in any courses before attempting to remove the course, and that it is within window.
    public void leaveCourse(String username, String courseToLeave) {
        Course course = courseRepository.findCourseByAbbreviation(courseToLeave);
        if (course==null){
            throw new NoSuchCourseException("This course does not exist!");
        }
        if(!courseValidatorService.isOpen(course)){
            throw new CourseNotOpenException("Course withdrawal window has ended!");
        }
        // Making sure user is not already registered
        if(!course.getStudentUsernames().contains(username)) {
             logger.info("User tried to leave a course they were already registered for");
             throw new NotRegisteredForCourseException("User isn't registered for this course!");
        }

        userCourseListRepo.removeCourseFromUserList(username, courseToLeave);
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