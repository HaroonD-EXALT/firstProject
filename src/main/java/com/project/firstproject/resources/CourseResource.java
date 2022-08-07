package com.project.firstproject.resources;

import com.project.firstproject.model.Course;
import com.project.firstproject.services.CourseService;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseResource {

    CourseService courseService = new CourseService();

    @GetMapping
    public List<Course> getAllCourses(){
        return  courseService.getAllCourses();
    }


    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable("id") long id){
        return courseService.getCourseById(id);
    }

    @PostMapping
    public Course addNewCourse(Course course){
        return courseService.insertCourse(course);
    }


    @DeleteMapping("/{id}")
    public Object deleteCourse(@PathVariable("id") long id){
        Course course =  courseService.deleteCourse(id);
        if (course != null){
            return course;

        }
        return Response.status(Response.Status.BAD_REQUEST).entity("there is students registered in this course").build();
    }
}
