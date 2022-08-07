package com.project.firstproject.resources;

import com.project.firstproject.model.Student;
import com.project.firstproject.services.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentResource {
    StudentService studentService = new StudentService();


    @GetMapping
    public List<Student> getStudents(){
        return studentService.getAllStudents();

    }
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") long id){
     return studentService.getStudentById(id);
    }

    @PostMapping
    public Student addStudent(Student student){
        System.err.println("post new Student");
        System.out.println(student);
        return  studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") long id){
        studentService.removeStudent(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") long id,Student student){
        return studentService.updateStudent(id,student);
    }
    @PostMapping("/{stuId}/courses/{courseId}")
    public Student registerCourse(@PathVariable("stuId") long stuId,@PathVariable("courseId") long courseId){
        return studentService.registerCourse(stuId,courseId);
    }

    @DeleteMapping("/{stuId}/courses/{courseId}")
    public Student deleteRegCourse(@PathVariable("stuId") long stuId,@PathVariable("courseId") long courseId){
        return studentService.deleteRegCourse(stuId,courseId);
    }

}
