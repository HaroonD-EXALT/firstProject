package com.project.firstproject.restServices;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.model.IService.IStudentService;
import com.project.firstproject.configuration.AerospikeDatabase;
import com.project.firstproject.domain.Course;
import com.project.firstproject.domain.Student;
import com.project.firstproject.model.dao.impl.CourseDao;
import com.project.firstproject.model.dao.impl.StudentDao;
import org.springframework.stereotype.Service;
import java.util.List;
;

@Service
public class StudentService implements IStudentService {
    private StudentDao studentDao = new StudentDao();
    private CourseDao courseDao = new CourseDao();

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
        return studentDao.update(id, newStudent);
    }

    @Override
    public Student registerCourse(long stuId, long courseId) throws AerospikeException, Exception {

        Student student = studentDao.getById(stuId);
        if (student == null) {
            throw new Exception("Student dose not exists!");
        }
        Course course =
                courseDao.getById(courseId);
        if (course == null) {
            throw new Exception("Course dose not exists!");
        }
        if (!student.isCourseRegistered(course)) {
            student.addNewCourse(course);
            course.incrementNumOfStu();
            studentDao.save(student);
            courseDao.save(course);
            System.out.println("course registered !");
        } else {
            throw new Exception("the course is already registered");
        }


        return student;
    }

    @Override
    public Student deleteRegCourse(long stuId, long courseId) throws AerospikeException, Exception {

        Student student = studentDao.getById(stuId);
        if (student == null) {
            throw new Exception("Student dose not exists!");
        }
        Course course = courseDao.getById(courseId);
        if (course == null) {
            throw new Exception("Course dose not exists!");
        }
        if (student.isCourseRegistered(course)) {
            student.removeCourse(course);
            course.decrementNumOfStu();
            studentDao.save(student);
            courseDao.save(course);
            System.out.println("registered course removed !");
        } else {
            throw new Exception("the course is already unregistered");
        }

        return student;
    }


}
