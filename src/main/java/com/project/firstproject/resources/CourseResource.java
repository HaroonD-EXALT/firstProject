package com.project.firstproject.resources;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.model.Admin;
import com.project.firstproject.model.Course;
import com.project.firstproject.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseResource {

    CourseService courseService = new CourseService();

    @GetMapping
    public ResponseEntity<Object> getAllCourses() {
        try {
            List<Course> courseList = courseService.getAllCourses();
            return ResponseEntity.status(HttpStatus.OK).body(courseList);

        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something wrong try again later ");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity getCourseById(@PathVariable("id") long id) {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
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

    @PostMapping
    public ResponseEntity addNewCourse(@Valid @RequestBody Course course) {
        try {
            Course courseAdd = courseService.insertCourse(course);
            return ResponseEntity.ok(courseAdd);

        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something wrong try again later ");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteCourse(@PathVariable("id") long id) {
        try {
            Course course = courseService.deleteCourse(id);
            return ResponseEntity.ok(course);

        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something wrong " + e.getMessage() + " try again later ");
        }
    }
}
