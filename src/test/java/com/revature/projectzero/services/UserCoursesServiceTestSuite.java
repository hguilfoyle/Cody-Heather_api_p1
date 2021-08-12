package com.revature.projectzero.services;

import com.revature.projectzero.documents.AppUser;
import com.revature.projectzero.repositories.UserCoursesRepository;
import com.revature.projectzero.util.UserSession;
import com.revature.projectzero.util.exceptions.AlreadyRegisteredForCourseException;
import com.revature.projectzero.util.exceptions.NoCoursesJoinedException;
import com.revature.projectzero.util.exceptions.NotRegisteredForCourseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class UserCoursesServiceTestSuite {

    // Instantiate system under test
    private UserCoursesService sut;

    // Mock dependencies required for the system under test
    private AppUser mockUser;
    private UserSession mockUserSession;
    private UserCoursesRepository mockUserCourseRepo;

    @Before
    public void setup(){

        mockUserCourseRepo = mock(UserCoursesRepository.class);
        mockUserSession = mock(UserSession.class);
        mockUser = mock(AppUser.class);
        mockUserSession.setCurrentUser(mockUser);
        sut = new UserCoursesService(mockUserCourseRepo,mockUserSession);
    }

    @After
    public void cleanUp(){
        mockUserCourseRepo = null;
        mockUserSession = null;
        sut = null;
    }


    // joinCourse tests


    @Test
    public void joinCourse_executesSuccessfully_whenGivenValidCourseName(){
        // Arrange
        String validCourse = "validCourseToJoin";
        List<String> validCourseList = new ArrayList<String>(){{add("Course1"); add("Course2");}};
        when(mockUserSession.getCurrentUser()).thenReturn(mockUser);
        when(mockUserCourseRepo.findRegisteredCoursesByUsername(mockUser.getUsername())).thenReturn(validCourseList);
        // Act
        sut.joinCourse(validCourse);
        // Assert
        verify(mockUserSession,times(1)).getCurrentUser();
        verify(mockUserCourseRepo,times(1)).findRegisteredCoursesByUsername(mockUser.getUsername());

    }


    @Test
    public void joinCourse_executesSuccessfully_whenGivenValidCourseName_ifNoCoursesHaveBeenJoined(){
        // Arrange
        String validCourse = "validCourseToJoin";
        List<String> validCourseList = new ArrayList<>();
        when(mockUserSession.getCurrentUser()).thenReturn(mockUser);
        when(mockUserCourseRepo.findRegisteredCoursesByUsername(mockUser.getUsername())).thenReturn(validCourseList);
        // Act
        sut.joinCourse(validCourse);
        // Assert
        verify(mockUserSession,times(1)).getCurrentUser();
        verify(mockUserCourseRepo,times(1)).findRegisteredCoursesByUsername(mockUser.getUsername());

    }

    @Test(expected = AlreadyRegisteredForCourseException.class)
    public void joinCourse_throwsException_whenGivenDuplicateCourseName(){
        // Arrange
        String duplicateCourse = "Course Already Joined!";
        List<String> originalCourseList = new ArrayList<>();
        originalCourseList.add(duplicateCourse);
        when(mockUserSession.getCurrentUser()).thenReturn(mockUser);
        when(mockUserCourseRepo.findRegisteredCoursesByUsername(mockUser.getUsername())).thenReturn(originalCourseList);
        // Act
        try{
            sut.joinCourse(duplicateCourse);
        } finally { // Assert
            verify(mockUserSession,times(1)).getCurrentUser();
            verify(mockUserCourseRepo,times(1)).findRegisteredCoursesByUsername(mockUser.getUsername());
        }

    }

    // getCourse tests

    @Test
    public void getCourses_returnsCourseList_whenListIsPopulated(){
        // Arrange
        List<String> validCourseList = new ArrayList<String>(){{add("Course1"); add("Course2");}};
        List<String> expectedCourseList = new ArrayList<String>(){{add("Course1"); add("Course2");}};
        when(mockUserSession.getCurrentUser()).thenReturn(mockUser);
        when(mockUserCourseRepo.findRegisteredCoursesByUsername(mockUser.getUsername())).thenReturn(validCourseList);

        // Act
        List<String> actualResult = sut.getCourses();

        // Assert
        Assert.assertEquals(expectedCourseList,actualResult);
        verify(mockUserSession, times(2)).getCurrentUser();
        verify(mockUserCourseRepo, times(2)).findRegisteredCoursesByUsername(mockUser.getUsername());

    }

    @Test(expected = NoCoursesJoinedException.class)
    public void getCourses_throwsException_whenUser_hasNotRegistered_forAnyCourses(){
        // Arrange
        List<String> emptyCourseList = new ArrayList<>();
        when(mockUserSession.getCurrentUser()).thenReturn(mockUser);
        when(mockUserCourseRepo.findRegisteredCoursesByUsername(mockUser.getUsername())).thenReturn(emptyCourseList);
        // Act
        try{
            sut.getCourses();
        } finally { // Assert
            verify(mockUserSession,times(1)).getCurrentUser();
            verify(mockUserCourseRepo, times(1)).findRegisteredCoursesByUsername(mockUser.getUsername());
        }
    }

    // leaveCourse tests
    @Test
    public void leaveCourse_executesSuccessfully_whenUser_hasRegistered_forCourse(){
        // Arrange
        String validCourseToLeave = "Course1";
        List<String> validCourseList = new ArrayList<String>(){{add("Course1"); add("Course2");}};
        when(mockUserSession.getCurrentUser()).thenReturn(mockUser);
        when(mockUserCourseRepo.findRegisteredCoursesByUsername(mockUser.getUsername())).thenReturn(validCourseList);

        // Act
        sut.leaveCourse(validCourseToLeave);
        // Assert
        verify(mockUserSession,times(1)).getCurrentUser();
        verify(mockUserCourseRepo,times(1)).findRegisteredCoursesByUsername(mockUser.getUsername());
        verify(mockUserCourseRepo,times(1))
                .removeCourseFromUserList(validCourseToLeave,mockUser.getUsername());

    }

    @Test(expected = NoCoursesJoinedException.class)
    public void leaveCourse_throwsException_whenUser_hasNotRegistered_forAnyCourse(){
        // Arrange
        List<String> emptyCourseList = new ArrayList<>();
        when(mockUserSession.getCurrentUser()).thenReturn(mockUser);
        when(mockUserCourseRepo.findRegisteredCoursesByUsername(mockUser.getUsername())).thenReturn(emptyCourseList);
        // Act
        try{
            sut.getCourses();
        } finally { // Assert
            verify(mockUserSession,times(1)).getCurrentUser();
            verify(mockUserCourseRepo, times(1)).findRegisteredCoursesByUsername(mockUser.getUsername());
        }
    }

    // verifyCourseEntry tests

    @Test
    public void verifyCourseEntry_returnsCourseName_whenUserAttempts_toWithdrawFromValidCourse(){
        // Arrange
        String validCourseToLeave = "Course1";
        String expectedCourseToLeave = "Course1";
        List<String> validCourseList = new ArrayList<String>(){{add("Course1"); add("Course2");}};

        // Act
        String actualResult = sut.verifyCourseEntry(validCourseList,validCourseToLeave);

        // Assert
        Assert.assertEquals(actualResult,expectedCourseToLeave);
    }

    @Test(expected = NotRegisteredForCourseException.class)
    public void verifyCourseEntry_throwsException_whenUserAttempts_toWithdrawFromInvalidCourse(){
        // Arrange
        String invalidCourseToLeave = "invalid-course";
        List<String> validCourseList = new ArrayList<String>(){{add("Course1"); add("Course2");}};

        // Act
        String exception = sut.verifyCourseEntry(validCourseList,invalidCourseToLeave);

    }

    //

}





