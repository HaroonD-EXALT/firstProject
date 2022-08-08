package com.project.firstproject.model;

import com.aerospike.mapper.annotations.AerospikeBin;
import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import org.springframework.context.annotation.ComponentScan;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ComponentScan
@AerospikeRecord(namespace = "test", set = "courses")
public class Course {
    @AerospikeKey
    @AerospikeBin(name = "PK")
    private long id;

    @NotNull(message = "name is required, must not be null")
    @NotEmpty(message = "name is required, must not be empty")
    @NotBlank(message = "name is required, must not be blank")
    private String name;

    private int numOfStudent;

    public Course() {
    }

    public Course(long id, String name) {
        this.id = id;
        this.name = name;
        this.numOfStudent = 0;

    }

    public int getNumOfStudent() {
        return numOfStudent;
    }

    public void incrementNumOfStu() {
        this.numOfStudent++;
    }

    public void decrementNumOfStu() {
        if (this.numOfStudent > 0)
            this.numOfStudent--;
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

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
