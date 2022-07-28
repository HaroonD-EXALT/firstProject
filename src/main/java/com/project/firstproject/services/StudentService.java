package com.project.firstproject.services;

import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private AerospikeDatabase database= AerospikeDatabase.getInstance();


    public List<Student> getAllStudents(){
        List<Student> studentList = database.getAllStudent();

        return studentList;
    }

    public Student getStudentById(long id){
        return database.getStudentById(id);
    }

    public Student addStudent(Student student) {
        return database.insertStudent(student);
    }

    public void removeStudent(long id) {
        database.deleteStudentById(id);
    }

    public Student updateStudent(long id, Student student) {
        return database.updateStudent(id,student);
    }

    public Student registerCourse(long stuId, long courseId) {
       return  database.registerCourse(stuId,courseId);
    }

    public Student deleteRegCourse(long stuId, long courseId) {
        return  database.removeRegCourse(stuId,courseId);
    }
}
