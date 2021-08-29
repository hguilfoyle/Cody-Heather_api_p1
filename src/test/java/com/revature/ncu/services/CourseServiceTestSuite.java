package com.revature.ncu.services;

import com.revature.ncu.datasources.documents.Course;
import com.revature.ncu.datasources.repositories.CourseRepository;
import com.revature.ncu.util.exceptions.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class CourseServiceTestSuite {

    // Instantiate system under test
    private CourseService sut;

    // Mock dependencies required for the system under test
    private CourseRepository mockCourseRepo;
    private CourseValidatorService mockValidator;

    @Before
    public void setup(){
        mockCourseRepo = mock(CourseRepository.class);
        mockValidator = mock(CourseValidatorService.class);
        sut = new CourseService(mockCourseRepo, mockValidator);
    }

    @After
    public void cleanUp(){
        mockCourseRepo = null;
        mockValidator = null;
        sut = null;
    }


    // add tests

    @Test
    public void add_returnsSuccessfully_whenGivenValidCourse(){
        //Arrange
        Course expectedResult = new Course("ValidCourse","VLDCS",
                "This is a valid course description");
        Course validCourse = new Course("ValidCourse","VLDCS",
                "This is a valid course description");
        when(mockValidator.newCourseEntryValidator(validCourse)).thenReturn(true);
        when(mockCourseRepo.findCourseByName(anyString())).thenReturn(null);
        when(mockCourseRepo.findCourseByAbbreviation(anyString())).thenReturn(null);
        when(mockCourseRepo.save(validCourse)).thenReturn(expectedResult);

        //Act
        Course actualResult = sut.add(validCourse);

        //Assert
        Assert.assertEquals(expectedResult,actualResult);
        verify(mockValidator,times(1)).newCourseEntryValidator(validCourse);
        verify(mockCourseRepo,times(1)).findCourseByName(anyString());
        verify(mockCourseRepo,times(1)).findCourseByAbbreviation(anyString());
        verify(mockCourseRepo,times(1)).save(validCourse);

    }

    @Test(expected = ResourcePersistenceException.class)
    public void add_throwsException_whenGivenCourse_withDuplicateCourseName(){
        //Arrange
        Course existingCourse = new Course("Duplicate","ORGNL",
                "Original course.");
        Course duplicate = new Course("Duplicate","TEST",
                "This course has a duplicate name");
        when(mockCourseRepo.findCourseByName(duplicate.getCourseName())).thenReturn(existingCourse);

        //Act
        try {
            sut.add(duplicate);
        }finally {

            //Assert
            verify(mockCourseRepo, times(1)).findCourseByName(duplicate.getCourseName());
            verify(mockCourseRepo, times(0)).save(duplicate);
        }

    }

    @Test(expected = ResourcePersistenceException.class)
    public void add_throwsException_whenGivenCourse_withDuplicateCourseAbbreviation(){
        //Arrange
        Course existingCourse = new Course("Original","DUPE",
                "Original course.");
        Course duplicate = new Course("Tester","DUPE",
                "This course has a duplicate abbreviation");
        when(mockCourseRepo.findCourseByAbbreviation(duplicate.getCourseAbbreviation())).thenReturn(existingCourse);

        //Act
        try {
            sut.add(duplicate);
        }finally {

            //Assert
            verify(mockCourseRepo, times(1))
                    .findCourseByAbbreviation(duplicate.getCourseAbbreviation());
            verify(mockCourseRepo, times(0)).save(duplicate);
        }

    }

    @Test(expected = InvalidEntryException.class)
    public void add_throwsException_whenGivenCourse_withEmptyValues(){
        //Arrange
        Course invalidCourse = new Course("","","");
        when(mockValidator.newCourseEntryValidator(invalidCourse)).thenThrow(InvalidEntryException.class);
        //Act
        try {
            sut.add(invalidCourse);
        }finally { //Assert
            verify(mockValidator,times(1)).newCourseEntryValidator(invalidCourse);
        }


    }


    // removeCourse Tests
    @Test
    public void removeCourse_executesSuccessfully_whenProvidedWithValidCourse(){
        //Arrange
        Course validCourse = new Course("VALID");
        Course expectedCourse = new Course("VALID");
        when(mockCourseRepo.findCourseByAbbreviation(validCourse.getCourseAbbreviation()))
                .thenReturn(expectedCourse);

        //Act
        sut.removeCourse(validCourse);

        //Assert
        verify(mockCourseRepo, times(1))
                .findCourseByAbbreviation(validCourse.getCourseAbbreviation());
        verify(mockCourseRepo, times(1)).removeCourseByAbbreviation(validCourse);

    }

    @Test(expected = InvalidEntryException.class)
    public void removeCourse_throwsException_whenGivenInvalidCourseAbbreviation() {
        //Arrange
        Course invalidCourse = new Course("NoVALID");
        when(mockCourseRepo.findCourseByAbbreviation(invalidCourse.getCourseAbbreviation()))
                .thenReturn(null);
        //Act
        sut.removeCourse(invalidCourse);
    }

    //update tests

    @Test
    public void updateCourse_returnsCourse_whenProvidedWithValidCourse(){
        // Arrange
        Course original = new Course("ValidCourse","VLD101","This is a valid course.",
                LocalDate.parse("2021-11-11"),LocalDate.parse("2021-11-13"),13);

        Course validUpdate = new Course("ValidUpdateCourse","VLD201","This is a valid changed course detail.",
                LocalDate.parse("2021-10-11"),LocalDate.parse("2022-10-13"),42);
        when(mockValidator.courseUpdateValidator(original,validUpdate)).thenReturn(true);
        when(mockCourseRepo.findCourseByAbbreviation("VLD201")).thenReturn(null);
        when(mockCourseRepo.findCourseByName("ValidUpdateCourse")).thenReturn(null);
        // Act
        Course actualResult = sut.updateCourse(original, validUpdate);
        // Assert
        Assert.assertEquals(actualResult,validUpdate);
    }

    @Test(expected = ResourcePersistenceException.class)
    public void updateCourse_throwsException_whenProvidedWithCourse_withDuplicateAbbreviation(){
        // Arrange
        Course original = new Course("ValidCourse","VLD101","This is a valid course.",
                LocalDate.parse("2021-11-11"),LocalDate.parse("2021-11-13"),13);

        Course duplicateAbv = new Course("Duplicate","DUPE","This is a valid changed course detail.",
                LocalDate.parse("2021-10-11"),LocalDate.parse("2022-10-13"),42);

        Course existingCourse = new Course("ValidUpdateCourse","DUPE","This is a valid changed course detail.",
                LocalDate.parse("2021-10-11"),LocalDate.parse("2022-10-13"),42);

        when(mockValidator.courseUpdateValidator(original,duplicateAbv)).thenReturn(true);
        when(mockCourseRepo.findCourseByAbbreviation("DUPE")).thenReturn(existingCourse);
        // Act
        try{
            sut.updateCourse(original, duplicateAbv);
        }finally {// Assert
            verify(mockValidator,times(1)).courseUpdateValidator(original,duplicateAbv);
            verify(mockCourseRepo,times(1)).findCourseByAbbreviation("DUPE");
        }
    }

    @Test(expected = ResourcePersistenceException.class)
    public void updateCourse_throwsException_whenProvidedWithCourse_withDuplicateName(){
        // Arrange
        Course original = new Course("ValidCourse","VLD101","This is a valid course.",
                LocalDate.parse("2021-11-11"),LocalDate.parse("2021-11-13"),13);

        Course duplicateAbv = new Course("Duplicate","VLD201","This is a valid changed course detail.",
                LocalDate.parse("2021-10-11"),LocalDate.parse("2022-10-13"),42);

        Course existingCourse = new Course("Duplicate","TEST","This is a valid course detail.",
                LocalDate.parse("2021-10-11"),LocalDate.parse("2022-10-13"),42);

        when(mockValidator.courseUpdateValidator(original,duplicateAbv)).thenReturn(true);
        when(mockCourseRepo.findCourseByAbbreviation("VLD201")).thenReturn(null);
        when(mockCourseRepo.findCourseByName("Duplicate")).thenReturn(existingCourse);
        // Act
        try{
            sut.updateCourse(original, duplicateAbv);
        }finally {// Assert
            verify(mockValidator,times(1)).courseUpdateValidator(original,duplicateAbv);
            verify(mockCourseRepo,times(1)).findCourseByAbbreviation("VLD201");
            verify(mockCourseRepo,times(1)).findCourseByName("Duplicate");
        }
    }


    @Test
    public void joinCourse_executesSuccessfully_whenProvidedWithValidAbbreviation(){
        //Arrange
        String joiningCourseAbv = "ABV101";
        Course expected = new Course();
        String username = "validusername";
        Set<String> studentUsernames = new HashSet<String>();
        when(mockCourseRepo.findCourseByAbbreviation(joiningCourseAbv)).thenReturn(expected);
        when(mockValidator.isOpen(expected)).thenReturn(true);
        //Act
        sut.joinCourse(joiningCourseAbv,username);
        //Assert
        verify(mockCourseRepo,times(1)).findCourseByAbbreviation(joiningCourseAbv);
        verify(mockValidator,times(1)).isOpen(expected);
        verify(mockCourseRepo,times(1)).addStudentUsername(joiningCourseAbv,username);
    }

    @Test(expected = NoSuchCourseException.class)
    public void joinCourse_throwsException_whenProvidedWithInValidAbbreviation() {
        //Arrange
        String joiningCourseAbv = "ABV101";
        Course expected = new Course();
        String username = "validusername";
        Set<String> studentUsernames = new HashSet<String>();
        when(mockCourseRepo.findCourseByAbbreviation(joiningCourseAbv)).thenReturn(null);
        when(mockValidator.isOpen(expected)).thenReturn(true);
        //Act
        sut.joinCourse(joiningCourseAbv, username);
        //Assert
        verify(mockCourseRepo, times(1)).findCourseByAbbreviation(joiningCourseAbv);
    }

    @Test(expected = CourseNotOpenException.class)
    public void joinCourse_throwsException_whenProvidedCourseIsClosed() {
        //Arrange
        String joiningCourseAbv = "ABV101";
        Course expected = new Course();
        String username = "validusername";
        Set<String> studentUsernames = new HashSet<String>();
        when(mockCourseRepo.findCourseByAbbreviation(joiningCourseAbv)).thenReturn(expected);
        when(mockValidator.isOpen(expected)).thenReturn(false);
        //Act
        sut.joinCourse(joiningCourseAbv, username);
        //Assert
        verify(mockCourseRepo, times(1)).findCourseByAbbreviation(joiningCourseAbv);
        verify(mockValidator,times(1)).isOpen(expected);
    }

    @Test(expected = AlreadyRegisteredForCourseException.class)
    public void joinCourse_throwsException_whenUserIsAlreadyInClass() {
        //Arrange
        String joiningCourseAbv = "ABV101";
        Course expected = new Course();
        String username = "validusername";

        Set<String> studentUsernames = new HashSet<>();
        studentUsernames.add("validusername");
        expected.setStudentUsernames(studentUsernames);

        when(mockCourseRepo.findCourseByAbbreviation(joiningCourseAbv)).thenReturn(expected);
        when(mockValidator.isOpen(expected)).thenReturn(true);
        //Act
        sut.joinCourse(joiningCourseAbv, username);
        //Assert
        verify(mockCourseRepo, times(1)).findCourseByAbbreviation(joiningCourseAbv);
        verify(mockValidator,times(1)).isOpen(expected);
    }


    // verifyCourse tests

    @Test
    public void findCourse_byAbbreviation_returnsCourse_whenProvidedWithValidAbbreviation(){
        // Arrange
        String validAbv = "VALID";
        Course expectedResult = new Course("ValidCourse","VALID",
                "This is a valid course description");
        Course validCourse = new Course("ValidCourse","VALID",
                "This is a valid course description");
        when(mockCourseRepo.findCourseByAbbreviation(validAbv)).thenReturn(validCourse);
        // Act
        Course actualResult = sut.findCourseByAbbreviation(validAbv);
        // Assert
        verify(mockCourseRepo, times(1)).findCourseByAbbreviation(validAbv);
        Assert.assertEquals(expectedResult,actualResult);

    }

    @Test(expected = ResourcePersistenceException.class)
    public void verifyCourse_throwsException_whenProvidedWithAbbreviation_thatDoesNotExist(){
        // Arrange
        String invalidAbv = "HELP";
        when(mockCourseRepo.findCourseByAbbreviation(invalidAbv)).thenReturn(null);

        // Act
        try {
            sut.findCourseByAbbreviation(invalidAbv);
        } finally {// Assert
            verify(mockCourseRepo,times(1)).findCourseByAbbreviation(invalidAbv);
        }
    }

    //verifyCourseOpenByAbbreviation Tests


    //verifyCourseOpenByName Tests


    //getCourses test

    @Test
    public void getCourses_returnsListOfOpenCourses_whenListIsPopulated(){
        // Arrange
        Course testCourse1 = new Course("","","");
        Course testCourse2 = new Course("","","");
        List<Course> validCourseList = new ArrayList<Course>();
            validCourseList.add(testCourse1);
            validCourseList.add(testCourse2);
        when(mockCourseRepo.retrieveOpenCourses()).thenReturn(validCourseList);
        // Act
        sut.getCourses();
        // Assert
        verify(mockCourseRepo,times(1)).retrieveOpenCourses();
    }

    @Test(expected = NoOpenCoursesException.class)
    public void getCourses_throwsException_whenNoOpenCoursesFound(){
        // Arrange
        List<Course> emptyCourseList = new ArrayList<Course>();
        when(mockCourseRepo.retrieveOpenCourses()).thenReturn(emptyCourseList);
        // Act
        sut.getCourses();

    }

}
