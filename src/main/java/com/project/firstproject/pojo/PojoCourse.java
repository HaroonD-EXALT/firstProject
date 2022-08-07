package com.project.firstproject.pojo;


import com.project.firstproject.model.Course;

import java.util.ArrayList;
import java.util.List;

public class PojoCourse {
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    private List<Course> courses = new ArrayList<>();
}
