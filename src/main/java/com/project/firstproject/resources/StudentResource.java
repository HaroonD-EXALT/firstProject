package com.project.firstproject.resources;

import com.project.firstproject.model.Student;
import com.project.firstproject.services.StudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
    StudentService studentService = new StudentService();

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<Student> getStudents(){
        return studentService.getAllStudents();

    }
    @GET
    @Path("/{id}")
    public Student getStudentById(@PathParam("id") long id){
     return studentService.getStudentById(id);
    }

    @POST
    public Student addStudent(Student student){
        System.err.println("post new Student");
        System.out.println(student);
        return  studentService.addStudent(student);
    }

    @DELETE
    @Path("/{id}")
    public void deleteStudent(@PathParam("id") long id){
        studentService.removeStudent(id);
    }

    @PUT
    @Path("/{id}")
    public Student updateStudent(@PathParam("id") long id,Student student){
        return studentService.updateStudent(id,student);
    }
    @POST
    @Path("/{stuId}/courses/{courseId}")
    public Student registerCourse(@PathParam("stuId") long stuId,@PathParam("courseId") long courseId){
        return studentService.registerCourse(stuId,courseId);
    }

    @DELETE
    @Path("/{stuId}/courses/{courseId}")
    public Student deleteRegCourse(@PathParam("stuId") long stuId,@PathParam("courseId") long courseId){
        return studentService.deleteRegCourse(stuId,courseId);
    }

}
