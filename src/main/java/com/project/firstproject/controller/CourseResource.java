package com.project.firstproject.controller;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.domain.Course;
import com.project.firstproject.model.dto.AdminDto;
import com.project.firstproject.model.dto.CourseDto;
import com.project.firstproject.restServices.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/courses")
public class CourseResource {

    CourseService courseService = new CourseService();
    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public ResponseEntity getAllCourses() {
        try {
            Course[] courseList = courseService.getAllCourses();

            return ResponseEntity.status(HttpStatus.OK).body(Arrays.stream(courseList).map(course -> modelMapper.map(course, CourseDto.class)));

        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something wrong try again later");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity getCourseById(@PathVariable("id") long id) {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok( modelMapper.map(course, CourseDto.class));
        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity addNewCourse(@Valid @RequestBody Course course) {
        try {
            Course courseAdded = courseService.insertCourse(course);
            return ResponseEntity.ok( modelMapper.map(courseAdded, CourseDto.class));

        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something wrong try again later");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteCourse(@PathVariable("id") long id) {
        try {
            Course course = courseService.deleteCourse(id);
            return ResponseEntity.ok( modelMapper.map(course, CourseDto.class));

        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
