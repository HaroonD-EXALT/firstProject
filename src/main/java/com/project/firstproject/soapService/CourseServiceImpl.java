package com.project.firstproject.soapService;

import com.project.firstproject.IServiceRepo.ICourseService;
import com.project.firstproject.model.Course;
import com.project.firstproject.restServices.CourseService;
import javax.jws.WebService;

@WebService(endpointInterface = "com.project.firstproject.IServiceRepo.ICourseService")
public class CourseServiceImpl implements ICourseService {
    private CourseService courseService = new CourseService();

    @Override
    public Course[] getAllCourses() {

        Course[] courseList = courseService.getAllCourses();
        return courseList;
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
