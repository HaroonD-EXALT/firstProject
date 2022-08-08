package com.project.firstproject.restServices;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.project.firstproject.IServiceRepo.ICourseService;
import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Course;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseService implements ICourseService {
    AerospikeDatabase database ;
    public CourseService() {
        this.database = AerospikeDatabase.getInstance();
    }
    @Override
    public Course[] getAllCourses() throws AerospikeException {
        List<Course> courseList = database.getMapper().scan(Course.class);
        return courseList.toArray(new Course[courseList.size()]);
    }
    @Override
    public Course insertCourse(Course course){
        Statement stmt = database.getStmt();
        stmt.setSetName("courses");
        long id = getNextId(stmt);
        System.out.println("Size: " + id);
        course.setId(id);
        //save object in the database, if it fails throws AerospikeException, and it will be caught in the resource class
        database.getMapper().save(course);
        System.out.println("Course Created");
        return course;

    }
    @Override
    public Course getCourseById(long id) throws AerospikeException,Exception{
            Course course= database.getMapper().read(Course.class,id);
            if (course == null) {
                throw new Exception("Course does not exist!");
            }
            System.out.println(course);
            return course;


    }
    @Override
    public Course deleteCourse(long id) throws AerospikeException,Exception{
        Course course = database.getMapper().read(Course.class,id);
        //there is any student registered in the course and can be safely deleted
        if (course == null){
            throw new Exception("course dose not exists!");
        }
        if (course.getNumOfStudent() == 0){
            database.getMapper().delete(course);
            System.out.println("Course removed !");
            return course;
        }
        // there is students registered in the course and can not be deleted
        throw new Exception("can not remove the course! there is students registered on it");
    }

    private long getNextId(Statement stat) {
        RecordSet rs = database.getAerospikeClient().query(null, stat);
        long id = StreamSupport.stream(rs.spliterator(), false).collect(Collectors.toList()).size();
        return id + 1;
    }
}
