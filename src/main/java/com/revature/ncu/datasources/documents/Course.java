package com.revature.ncu.datasources.documents;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.Objects;


// Course POJO
public class Course {

    private String id;
    private String courseName;
    private String courseAbbreviation;
    private String courseDetail;
    private String professorName;
    //TODO logic for these
    private LocalDateTime courseOpenDate;
    private LocalDateTime courseCloseDate;
    private int courseCapacity;

    public Course(){ super(); }

    public Course(String cn, String cAbv){
        this.courseName = cn;
        this.courseAbbreviation = cAbv;
    }

    public Course(String cn, String cAbv, String detail) {
        this.courseName = cn;
        this.courseAbbreviation = cAbv;
        this.courseDetail = detail;
    }


    public Course(String id, String cn, String cAbv, String detail, String prof,
                  LocalDateTime openDate, LocalDateTime closeDate){
        this.id = id;
        this.courseName = cn;
        this.courseAbbreviation = cAbv;
        this.courseDetail = detail;
        this.professorName = prof;
        this.courseOpenDate = openDate;
        this.courseCloseDate = closeDate;

    }



    // Setters and getters
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(String courseDetail) {
        this.courseDetail = courseDetail;
    }

    public void setId(String id) { this.id = id; }

    public String getId() {
        return id;
    }

    public String getCourseAbbreviation() { return courseAbbreviation; }

    public void setCourseAbbreviation(String courseAbbreviation) { this.courseAbbreviation = courseAbbreviation; }

    public String getProfessorName() { return professorName; }

    public void setProfessorName(String professorName) { this.professorName = professorName;}

    public LocalDateTime getCourseOpenDate() { return courseOpenDate; }

    public void setCourseOpenDate(LocalDateTime courseOpenDate) { this.courseOpenDate = courseOpenDate; }

    public LocalDateTime getCourseCloseDate() { return courseCloseDate; }

    public void setCourseCloseDate(LocalDateTime courseCloseDate) { this.courseCloseDate = courseCloseDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(courseName, course.courseName) && Objects.equals(courseAbbreviation, course.courseAbbreviation) && Objects.equals(courseDetail, course.courseDetail) && Objects.equals(professorName, course.professorName) && Objects.equals(courseOpenDate, course.courseOpenDate) && Objects.equals(courseCloseDate, course.courseCloseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, courseAbbreviation, courseDetail, professorName, courseOpenDate, courseCloseDate);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseAbbreviation='" + courseAbbreviation + '\'' +
                ", courseDetail='" + courseDetail + '\'' +
                ", professorName='" + professorName + '\'' +
                ", courseOpenDate=" + courseOpenDate +
                ", courseCloseDate=" + courseCloseDate +
                '}';
    }
}
