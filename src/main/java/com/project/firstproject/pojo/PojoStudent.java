package com.project.firstproject.pojo;

import com.project.firstproject.model.Student;

import java.util.ArrayList;
import java.util.List;

public class PojoStudent {

    private List<Student>  students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
