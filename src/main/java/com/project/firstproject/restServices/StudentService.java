package com.project.firstproject.restServices;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.project.firstproject.model.IService.IStudentService;
import com.project.firstproject.configuration.AerospikeDatabase;
import com.project.firstproject.domain.Course;
import com.project.firstproject.domain.Student;
import com.project.firstproject.model.dao.impl.StudentDao;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentService implements IStudentService {
    private AerospikeDatabase database = AerospikeDatabase.getInstance();
    private StudentDao studentDao = new StudentDao();
    @Override
    public Student[] getAllStudents() throws AerospikeException {
        List<Student> studentList = studentDao.getAll();
        return studentList.toArray(new Student[studentList.size()]);
    }
    @Override
    public Student getStudentById(long id) throws AerospikeException, Exception {
       return studentDao.getById(id);
    }
    @Override
    public Student addStudent(Student student) {
       return studentDao.save(student);
    }
    @Override
    public Student removeStudent(long id) throws AerospikeException, Exception {
        return studentDao.delete(id);
    }
    @Override
    public Student updateStudent(long id, Student newStudent) throws AerospikeException, Exception {
       return studentDao.update(id,newStudent);
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
        }else {
            throw new Exception("the course is already registered");
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
        }else {
            throw new Exception("the course is already unregistered");
        }

        return student;
    }

    private long getNextId(Statement stat) {
        RecordSet rs = database.getAerospikeClient().query(null, stat);
        long id = StreamSupport.stream(rs.spliterator(), false).collect(Collectors.toList()).size();
        return id + 1;
    }
}
