package com.revature.ncu.web.dtos;

/*
 * DTO = Data Transfer object.
 * This is a wrapper for an object to be transferred to the front end.
 */


import com.revature.ncu.datasources.documents.UserCourses;

import java.util.ArrayList;
import java.util.Objects;

public class UserCoursesDTO {

    private String id;
    private String username;
    private ArrayList<String> courses;

    public UserCoursesDTO(UserCourses subject){
        this.id = subject.getId();
        this.username = subject.getUsername();
        this.courses = subject.getCourses();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCoursesDTO that = (UserCoursesDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, courses);
    }

    @Override
    public String toString() {
        return "UserCoursesDTO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", courses=" + courses +
                '}';
    }
}
