package com.project.firstproject.model.dto;

import com.project.firstproject.domain.Course;

import java.util.List;

public class StudentDto {

    private long id;
    private String name;
    private List<Course> regCourses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getRegCourses() {
        return regCourses;
    }

    public void setRegCourses(List<Course> regCourses) {
        this.regCourses = regCourses;
    }
}
