package com.project.firstproject.IServiceRepo;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.model.Student;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IStudentService {

    public Object getAllStudents() throws AerospikeException;
    public Student getStudentById(long id) throws AerospikeException, Exception;
    public Student addStudent(Student student);
    public Student removeStudent(long id) throws AerospikeException, Exception;
    public Student updateStudent(long id, Student newStudent) throws AerospikeException, Exception;
    public Student registerCourse(long stuId, long courseId) throws AerospikeException,Exception;
    public Student deleteRegCourse(long stuId, long courseId) throws AerospikeException,Exception;

}
