package com.project.firstproject.restServices;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.model.IService.ICourseService;
import com.project.firstproject.configuration.AerospikeDatabase;
import com.project.firstproject.domain.Course;
import com.project.firstproject.model.dao.impl.CourseDao;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CourseService implements ICourseService {

    CourseDao courseDao = new CourseDao();
    @Override
    public Course[] getAllCourses() throws AerospikeException {
        List<Course> courses =  courseDao.getAll();
        return  courses.toArray(new Course[courses.size()]);
    }
    @Override
    public Course insertCourse(Course course){
        return courseDao.save(course);

    }
    @Override
    public Course getCourseById(long id) throws AerospikeException,Exception{
            return courseDao.getById(id);


    }
    @Override
    public Course deleteCourse(long id) throws AerospikeException,Exception{
       return courseDao.delete(id);
    }


}
