package com.project.firstproject.restServices;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.project.firstproject.IServiceRepo.IStudentService;
import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Course;
import com.project.firstproject.model.Student;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentService implements IStudentService {
    private AerospikeDatabase database = AerospikeDatabase.getInstance();
    @Override
    public Student[] getAllStudents() throws AerospikeException {
        List<Student> studentList = database.getMapper().scan(Student.class);
        return studentList.toArray(new Student[studentList.size()]);
    }
    @Override
    public Student getStudentById(long id) throws AerospikeException, Exception {
        Student student = database.getMapper().read(Student.class, id);
        if (student == null) {
            throw new Exception("Student dose not exists");
        }
        return student;
    }
    @Override
    public Student addStudent(Student student) {
        Statement stmt = database.getStmt();
        stmt.setSetName("students");
        long id = this.getNextId(stmt);
        System.out.println("Size: " + id);
        student.setId(id);
        student.setRegCourses(new ArrayList<>());
        student.setRole("student");
        database.getMapper().save(student);
        System.out.println("Student Created");
        return student;
    }
    @Override
    public Student removeStudent(long id) throws AerospikeException, Exception {
        Student student = database.getMapper().read(Student.class, id);
        //there is any student registered in the course and can be safely deleted
        if (student == null) {
            throw new Exception("Student dose not exists!");
        }

        database.getMapper().delete(student);
        System.out.println("Student removed !");
        return student;

    }
    @Override
    public Student updateStudent(long id, Student newStudent) throws AerospikeException, Exception {
        Key key = new Key(database.getNAMESPACE(), "students", id);
        Student student = getStudentById(id);
        if (student == null) {
            throw new Exception("Student dose not exists!");
        }
        student.setName(newStudent.getName());
        Bin name = new Bin("name", student.getName());
        database.getAerospikeClient().put(database.getWritePolicy(), key, name);
        return student;
    }
    @Override
    public Student registerCourse(long stuId, long courseId) throws AerospikeException,Exception {

        Student student = database.getMapper().read(Student.class, stuId);
        if (student == null) {
            throw new Exception("Student dose not exists!");
        }
        Course course = database.getMapper().read(Course.class, courseId);
        if (course == null) {
            throw new Exception("Course dose not exists!");
        }
        if (!student.isCourseRegistered(course)) {
            student.addNewCourse(course);
            course.incrementNumOfStu();
            database.getMapper().save(course);
            database.getMapper().save(student);
            System.out.println("course registered !");
        }


        return student;
    }
    @Override
    public Student deleteRegCourse(long stuId, long courseId) throws AerospikeException,Exception {

        Student student = database.getMapper().read(Student.class, stuId);
        if (student == null) {
            throw new Exception("Student dose not exists!");
        }
        Course course = database.getMapper().read(Course.class, courseId);
        if (course == null) {
            throw new Exception("Course dose not exists!");
        }
        if (student.isCourseRegistered(course)) {
            student.removeCourse(course);
            course.decrementNumOfStu();
            database.getMapper().save(student);
            database.getMapper().save(course);
            System.out.println("registered course removed !");
        }

        return student;
    }

    private long getNextId(Statement stat) {
        RecordSet rs = database.getAerospikeClient().query(null, stat);
        long id = StreamSupport.stream(rs.spliterator(), false).collect(Collectors.toList()).size();
        return id + 1;
    }
}
