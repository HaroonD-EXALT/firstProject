package com.project.firstproject.resources;

import com.project.firstproject.model.Course;
import com.project.firstproject.services.CourseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseResource {

    CourseService courseService = new CourseService();

    @GET
    public List<Course> getAllCourses(){
        return  courseService.getAllCourses();
    }

    @GET
    @Path("/{id}")
    public Course getCourseById(@PathParam("id") long id){
        return courseService.getCourseById(id);
    }

    @POST
    public Course addNewCourse(Course course){
        return courseService.insertCourse(course);
    }

    @DELETE
    @Path("/{id}")
    public Object deleteCourse(@PathParam("id") long id){
        Course course =  courseService.deleteCourse(id);
        if (course != null){
            return course;

        }
        return Response.status(Response.Status.BAD_REQUEST).entity("there is students registered in this course").build();
    }
}
