package com.revature.projectzero.documents;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;


// Course POJO
// TODO: Map BsonProperties

@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {

    private String id;
    private String courseName;
    private String courseAbbreviation;
    private String courseDetail;
    private boolean isOpen;

    //Jackson requires a no-args constructor
    public Course(){ super(); }

    public Course(String cn, String cAbv){
        this.courseName = cn;
        this.courseAbbreviation = cAbv;
    }

    public Course(String cn, String cAbv, String detail, boolean open) {
        this.courseName = cn;
        this.courseAbbreviation = cAbv;
        this.courseDetail = detail;
        this.isOpen = open;
    }


    public Course(String id, String cn, String cAbv, String detail){
        this.id = id;
        this.courseName = cn;
        this.courseAbbreviation = cAbv;
        this.courseDetail = detail;
    }

    public Course(String id, String cn, String cAbv, String detail, boolean open){
        this.id = id;
        this.courseName = cn;
        this.courseAbbreviation = cAbv;
        this.courseDetail = detail;
        this.isOpen = open;
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

    public boolean isOpen() { return isOpen; }

    public void setOpen(boolean open) { isOpen = open; }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, courseAbbreviation, courseDetail, isOpen);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj ) return true;
        else if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return Objects.equals(id, course.id) && Objects.equals(courseName, course.courseName) && Objects.equals(courseAbbreviation, course.courseAbbreviation) && Objects.equals(courseDetail, course.courseDetail) && Objects.equals(isOpen, course.isOpen);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseAbbreviation='" + courseAbbreviation + '\'' +
                ", courseDetail='" + courseDetail + '\'' +
                ", isOpen='" + isOpen + '\'' +
                '}';
    }
}
