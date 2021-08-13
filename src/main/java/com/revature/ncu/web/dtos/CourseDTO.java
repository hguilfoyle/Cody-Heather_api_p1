package com.revature.ncu.web.dtos;

/*
 * DTO = Data Transfer object.
 * This is a wrapper for an object to be transferred to the front end.
 */

import com.revature.ncu.datasources.documents.Course;

import java.time.LocalDateTime;
import java.util.Objects;

public class CourseDTO {

    private String id;
    private String courseName;
    private String courseAbbreviation;
    private String courseDetail;
    private String professorName;
    private String courseOpenDate;      //TODO logic for these
    private String courseCloseDate;     // Not sure if we want them displayed as strings, though...

    public CourseDTO(Course subject) {
        this.id = subject.getId();
        this.courseName = subject.getCourseName();
        this.courseDetail = subject.getCourseAbbreviation();
        this.professorName = subject.getCourseDetail();
        this.courseOpenDate = subject.getCourseOpenDate().toString();
        this.courseCloseDate = subject.getCourseCloseDate().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseAbbreviation() {
        return courseAbbreviation;
    }

    public void setCourseAbbreviation(String courseAbbreviation) {
        this.courseAbbreviation = courseAbbreviation;
    }

    public String getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(String courseDetail) {
        this.courseDetail = courseDetail;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getCourseOpenDate() {
        return courseOpenDate;
    }

    public void setCourseOpenDate(String courseOpenDate) {
        this.courseOpenDate = courseOpenDate;
    }

    public String getCourseCloseDate() {
        return courseCloseDate;
    }

    public void setCourseCloseDate(String courseCloseDate) {
        this.courseCloseDate = courseCloseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return Objects.equals(id, courseDTO.id) && Objects.equals(courseName, courseDTO.courseName) && Objects.equals(courseAbbreviation, courseDTO.courseAbbreviation) && Objects.equals(courseDetail, courseDTO.courseDetail) && Objects.equals(professorName, courseDTO.professorName) && Objects.equals(courseOpenDate, courseDTO.courseOpenDate) && Objects.equals(courseCloseDate, courseDTO.courseCloseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, courseAbbreviation, courseDetail, professorName, courseOpenDate, courseCloseDate);
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "id='" + id + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseAbbreviation='" + courseAbbreviation + '\'' +
                ", courseDetail='" + courseDetail + '\'' +
                ", professorName='" + professorName + '\'' +
                ", courseOpenDate='" + courseOpenDate + '\'' +
                ", courseCloseDate='" + courseCloseDate + '\'' +
                '}';
    }
}
