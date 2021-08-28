package com.revature.ncu.datasources.documents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// UserCourses POJO
public class UserCourses {

    private String id;
    private String username;
    private List<String> courses = new ArrayList<>();

    // Jackson requires a no-args constructor (but I guess I didn't use Jackson for this one...)
    public UserCourses(){super();}

    public UserCourses(String username){ this.username = username; }

    // For adding multiple courses, was not implemented in p0 but might have come in handy some time.
    public UserCourses addCourses(String... regCourses){
        this.courses.addAll(Arrays.asList(regCourses));
        return this;
    }

    // Setters and Getters

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    //Overrides
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserCourses that = (UserCourses) obj;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, courses);
    }

    @Override
    public String toString() {
        return "UserFavorites{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", Courses=" + courses +
                '}';
    }


}
