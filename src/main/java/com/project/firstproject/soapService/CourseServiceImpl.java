package com.project.firstproject.soapService;

import com.project.firstproject.IServiceRepo.ICourseService;
import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Course;
import com.project.firstproject.pojo.PojoCourse;
import com.project.firstproject.restServices.CourseService;


import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "com.project.firstproject.IServiceRepo.ICourseService")
public class CourseServiceImpl implements ICourseService {
    private AerospikeDatabase database = AerospikeDatabase.getInstance();
    private CourseService courseService = new CourseService();

    @Override
    public PojoCourse getAllCourses() {

        List<Course> courseList = courseService.getAllCourses();
        PojoCourse pojoCourse = new PojoCourse();
        pojoCourse.setCourses(courseList);
        return pojoCourse;
    }

    @Override
    public Course insertCourse(Course course) {
        return courseService.insertCourse(course);
    }

    @Override
    public Course getCourseById(long id) throws Exception {
        return courseService.getCourseById(id);
    }

    @Override
    public Course deleteCourse(long id) throws Exception {
        Course course = courseService.getCourseById(id);
        if (course.getNumOfStudent() == 0){
            courseService.deleteCourse(id);
            return course;
        }
        return null;
    }
}
