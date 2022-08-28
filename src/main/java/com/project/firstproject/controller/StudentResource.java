package com.project.firstproject.controller;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.domain.Student;
import com.project.firstproject.restServices.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentResource {
    StudentService studentService = new StudentService();


    @GetMapping
    public ResponseEntity getStudents(){
        try {
            Student[] student = studentService.getAllStudents();
            return ResponseEntity.ok(student);

        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something wrong " + e.getMessage() + " try again later");
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity getStudentById(@PathVariable("id") long id){
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok(student);

        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something wrong " + e.getMessage() + " try again later");
        }
    }

    @PostMapping("/create")
    public Student addStudent(@Valid @RequestBody Student student){
        System.err.println("post new Student");
        System.out.println(student);
        return  studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable("id") long id){
        try {
            Student student = studentService.removeStudent(id);
            return ResponseEntity.ok(student);

        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something wrong " + e.getMessage() + " try again later");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStudent(@PathVariable("id") long id,@Valid @RequestBody Student student){
        try {
            Student newStudent = studentService.updateStudent(id,student);
            return ResponseEntity.ok(newStudent);

        } catch (AerospikeException ae) {
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something wrong " + e.getMessage() + " try again later");
        }
    }
    @PostMapping("/{stuId}/courses/{courseId}")
    public ResponseEntity registerCourse(@PathVariable("stuId") long stuId,@PathVariable("courseId") long courseId){
        try {
            Student newStudent = studentService.registerCourse(stuId,courseId);
            return ResponseEntity.ok(newStudent);

        } catch (AerospikeException ae) {
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{stuId}/courses/{courseId}")
    public ResponseEntity deleteRegCourse(@PathVariable("stuId") long stuId,@PathVariable("courseId") long courseId){
        try {
            Student newStudent = studentService.deleteRegCourse(stuId,courseId);
            return ResponseEntity.ok(newStudent);

        } catch (AerospikeException ae) {
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            e.printStackTrace();


            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() );
        }
    }

}
