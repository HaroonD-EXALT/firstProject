package com.project.firstproject.services;

import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Course;

import java.util.List;

public class CourseService {
    AerospikeDatabase database = AerospikeDatabase.getInstance();
    public List<Course> getAllCourses() {
       return database.getAllCourses();
    }

    public Course insertCourse(Course course){
        return database.insertCourse(course);
    }

    public Course getCourseById(long id){
        return database.getCourseById(id);
    }

    public Course deleteCourse(long id) {
        Course course = database.getCourseById(id);
        if (course.getNumOfStudent() == 0){
            database.removeCourse(id);
            return course;
        }
        return null;
    }
}
