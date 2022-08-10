package com.project.firstproject.model.dao.impl;

import com.aerospike.client.query.Statement;
import com.project.firstproject.configuration.AerospikeDatabase;
import com.project.firstproject.domain.Student;
import com.project.firstproject.model.dao.Dao;

import java.util.ArrayList;
import java.util.List;

public class StudentDao implements Dao<Student> {

    private AerospikeDatabase database = AerospikeDatabase.getInstance();
    @Override
    public Student save(Student item) {
        Statement stmt = database.getStmt();
        stmt.setSetName("students");
        long id = getNextId();
        System.out.println("Size: " + id);
        item.setId(id);
        item.setRegCourses(new ArrayList<>());
        item.setRole("student");
        database.getMapper().save(item);
        System.out.println("Student Created");
        return item;
    }

    @Override
    public Student getById(long id) throws Exception {
        Student student = database.getMapper().read(Student.class, id);
        if (student == null) {
            throw new Exception("Student dose not exists");
        }
        return student;
    }

    @Override
    public List<Student> getAll() {
        List<Student> studentList = database.getMapper().scan(Student.class);
        return studentList;
    }

    @Override
    public Student update(long id, Student item) {
        return null;
    }

    @Override
    public Student delete(long id) throws Exception {
        Student student = database.getMapper().read(Student.class, id);
        //there is any student registered in the course and can be safely deleted
        if (student == null) {
            throw new Exception("Student dose not exists!");
        }

        database.getMapper().delete(student);
        System.out.println("Student removed !");
        return student;

    }

    private long getNextId() {
        return database.getMapper().scan(Student.class).size() +1;
    }
}
