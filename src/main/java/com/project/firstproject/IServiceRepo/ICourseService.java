package com.project.firstproject.IServiceRepo;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.model.Course;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ICourseService {
    @WebMethod

    public Object getAllCourses() throws AerospikeException;
    @WebMethod
    public Course insertCourse(Course course);
    @WebMethod
    public Course getCourseById(long id) throws AerospikeException,Exception;
    @WebMethod
    public Course deleteCourse(long id) throws AerospikeException,Exception;

}
