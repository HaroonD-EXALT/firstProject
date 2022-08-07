package com.project.firstproject.soapService;

import com.project.firstproject.IServiceRepo.IStudentService;
import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Student;
import com.project.firstproject.pojo.PojoStudent;
import com.project.firstproject.restServices.StudentService;


import javax.jws.WebService;
import java.util.List;
@WebService(endpointInterface = "com.project.firstproject.IServiceRepo.IStudentService")
public class StudentServiceImpl implements IStudentService {
    private AerospikeDatabase database= AerospikeDatabase.getInstance();
    private StudentService studentService = new StudentService();
    @Override
    public PojoStudent getAllStudents() {

        List<Student> students = studentService.getAllStudents();
        PojoStudent pojoStudent = new PojoStudent();
        pojoStudent.setStudents(students);
        return pojoStudent;
    }

    @Override
    public Student getStudentById(long id) throws Exception {
        return studentService.getStudentById(id);
    }

    @Override
    public Student addStudent(Student student) {
        return studentService.addStudent(student);
    }

    @Override
    public Student removeStudent(long id) throws Exception {
        return  studentService.removeStudent(id);

    }

    @Override
    public Student updateStudent(long id, Student student) throws Exception {
        return studentService.updateStudent(id,student);
    }

    @Override
    public Student registerCourse(long stuId, long courseId) throws Exception {
        return  studentService.registerCourse(stuId,courseId);
    }

    @Override
    public Student deleteRegCourse(long stuId, long courseId) throws Exception {
        return  studentService.deleteRegCourse(stuId,courseId);
    }
}
