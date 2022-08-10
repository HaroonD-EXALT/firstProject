package com.project.firstproject.model.dao.impl;

import com.project.firstproject.configuration.AerospikeDatabase;
import com.project.firstproject.domain.Course;
import com.project.firstproject.domain.Student;
import com.project.firstproject.model.dao.Dao;

import java.util.List;

public class CourseDao implements Dao<Course> {

    AerospikeDatabase database = AerospikeDatabase.getInstance();
    @Override
    public Course save(Course item) {
        long id = getNextId();
        System.out.println("Size: " + id);
        item.setId(id);
        //save object in the database, if it fails throws AerospikeException, and it will be caught in the resource class
        database.getMapper().save(item);
        System.out.println("Course Created");
        return item;
    }

    @Override
    public Course getById(long id) throws Exception {
        Course course= database.getMapper().read(Course.class,id);
        if (course == null) {
            throw new Exception("Course does not exist!");
        }
        System.out.println(course);
        return course;
    }

    @Override
    public List<Course> getAll() {
        List<Course> courseList = database.getMapper().scan(Course.class);
        return courseList;
    }

    @Override
    public Course update(long id, Course item) {
        return null;
    }

    @Override
    public Course delete(long id) throws Exception {
        Course course = database.getMapper().read(Course.class,id);
        //there is any student registered in the course and can be safely deleted
        if (course == null){
            throw new Exception("course not exists!");
        }
        if (course.getNumOfStudent() == 0){
            database.getMapper().delete(course);
            System.out.println("Course removed !");
            return course;
        }
        // there is students registered in the course and can not be deleted
        throw new Exception("can not remove the course! there is students registered on it");
    }

    private long getNextId() {
        return database.getMapper().scan(Course.class).size() +1;
    }
}
