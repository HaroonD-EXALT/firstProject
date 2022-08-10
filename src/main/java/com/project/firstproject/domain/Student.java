package com.project.firstproject.domain;

import com.aerospike.mapper.annotations.AerospikeBin;
import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@AerospikeRecord(namespace = "test", set = "students")
public class Student {
    @AerospikeKey
    @AerospikeBin(name = "PK")
    private long id;

    @NotNull(message = "name is required, must not be null")
    @NotEmpty(message = "name is required, must not be empty")
    @NotBlank(message = "name is required, must not be blank")
    private String name;


    private String role;

    private List<Course> regCourses;

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", regCourses=" + regCourses +
                '}';
    }

    public Student(long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.regCourses = new ArrayList<>();
    }

    public Student(long id, String name) {
        this.id = id;
        this.name = name;
        this.role = "student";
        this.regCourses = new ArrayList<>();
    }

    public List<Course> getRegCourses() {
        return regCourses;
    }

    public void setRegCourses(List<Course> regCourses) {
        this.regCourses = regCourses;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public void addNewCourse(Course course) {

        this.getRegCourses().add(course);
    }

    public void removeCourse(Course course) {

        if (isCourseRegistered(course)) {
            Course c = this.getRegCourses().stream().filter(course1 -> course1.getId() == course.getId()).findFirst().orElse(null);
            this.getRegCourses().remove(c);

            System.out.println("removed !");
        }
    }

    public boolean isCourseRegistered(Course course){

        Course c = this.getRegCourses().stream().filter(course1 -> course1.getId() == course.getId()).findFirst().orElse(null);
        if (c == null) {
            return false;
        }
        return true;
    }
}
