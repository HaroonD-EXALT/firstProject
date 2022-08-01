package com.project.firstproject.database;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.KeyRecord;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.aerospike.mapper.tools.AeroMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.firstproject.model.Admin;
import com.project.firstproject.model.Course;
import com.project.firstproject.model.Student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class AerospikeDatabase {
    private static AerospikeDatabase database;
    private static AerospikeClient aerospikeClient;
    private static WritePolicy writePolicy;
    private static final String NAMESPACE = "test";
    private Statement stmt ;

    AeroMapper mapper ;

    Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    String toJsonString(Object object) {
        return gson.toJson(object).toString();
    }

    private AerospikeDatabase() {
        aerospikeClient = new AerospikeClient("localhost", 3000);
        writePolicy = new WritePolicy();
        stmt= new Statement();
        stmt.setNamespace(NAMESPACE);
        mapper = new AeroMapper.Builder(aerospikeClient).build();
    }

    public static AerospikeDatabase getInstance() {
        if (aerospikeClient == null) {
            database = new AerospikeDatabase();
            return database;
        }
        return database;

    }

    private long getNextId(Statement stat) {
        RecordSet rs = aerospikeClient.query(null, stat);
        long id = StreamSupport.stream(rs.spliterator(), false).collect(Collectors.toList()).size();
        return id + 1;
    }

    public Student insertStudent(Student student) {
        stmt.setSetName("students");
        long id = this.getNextId(stmt);
        System.out.println("Size: " + id);
        student.setId(id);
        student.setRegCourses(student.getRegCourses());
        student.setRole("student");
        mapper.save(student);
//        Key key = new Key(NAMESPACE, "students", student.getId());
//        Bin PK = new Bin("PK", student.getId());
//        Bin name = new Bin("name", student.getName());
//        Bin role = new Bin("role", student.getRole());
//        aerospikeClient.put(writePolicy, key, PK, name, role);
        System.out.println("Student Created");
        return student;
    }

    public List<Student> getAllStudent() {
        stmt.setSetName("students");
        List<Student> studentList = mapper.scan(Student.class);
//        RecordSet rs = aerospikeClient.query(null, stmt);
//
//        List<Student> studentList = StreamSupport.stream(rs.spliterator(), false).map( rec ->{
//            Map<String, Object> bins = rec.record.bins;
////            aerospikeClient.delete(writePolicy,rec.key);
//            Student stu = new Student((Long) bins.get("PK"), (String) bins.get("name"));
////            System.err.println(stu);
//            return stu;
//        }).collect(Collectors.toList());
//        System.out.println(stuList.toString());
        return studentList;
    }

    public Student getStudentById(long id) {
        try{
            System.out.println(id);
//            Key key = new Key(NAMESPACE,"students",id);
//            Record record = aerospikeClient.get(null,key);
//            System.out.println(record.bins.get("PK"));
            Student student = mapper.read(Student.class,id);
            System.out.println(student);
            return student;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void deleteStudentById(long id) {
        Key key = new Key(NAMESPACE,"students",id);
        aerospikeClient.delete(writePolicy,key);
    }

    public Student updateStudent(long id, Student newStudent) {
        Key key = new Key(NAMESPACE,"students",id);
        Student student = getStudentById(id);
        student.setName(newStudent.getName());
        student.setRole(newStudent.getRole());

        Bin PK = new Bin("PK", student.getId());
        Bin name = new Bin("name", student.getName());
        Bin role = new Bin("role", student.getRole());
        aerospikeClient.put(writePolicy,key,PK,name,role);
        return student;
    }


    // admin method
    public Admin insertAdmin(Admin admin) {
        stmt.setSetName("admins");
        long id = this.getNextId(stmt);
        System.out.println("Size: " + id);
        admin.setId(id);
        admin.setRole("admin");
        Key key = new Key(NAMESPACE, "admins", admin.getId());
        Bin PK = new Bin("PK", admin.getId());
        Bin name = new Bin("name", admin.getName());
        Bin password = new Bin("password", admin.getPassword());
        Bin role = new Bin("role", admin.getRole());
        aerospikeClient.put(writePolicy, key, PK, name, password,role);
        System.out.println("Admin Created");
        return admin;
    }

    public List<Admin> getAllAdmins() {
        stmt.setSetName("admins");
        RecordSet rs = aerospikeClient.query(null, stmt);

        List<Admin> adminList = StreamSupport.stream(rs.spliterator(), false).map( rec ->{
            Map<String, Object> bins = rec.record.bins;
//            aerospikeClient.delete(writePolicy,rec.key);
            Admin admin = new Admin((Long) bins.get("PK"), (String) bins.get("name"));
//            System.err.println(stu);
            return admin;
        }).collect(Collectors.toList());
//        System.out.println(stuList.toString());
        return adminList;
    }

    public Admin getAdminById(long id) {
        try{
            Key key = new Key(NAMESPACE,"admins",id);
            Record record = aerospikeClient.get(null,key);
            System.out.println(record.bins.get("PK"));
            Admin admin = new Admin((Long) record.bins.get("PK"), (String) record.bins.get("name"),(String) record.bins.get("password"));
            System.out.println(admin);
            return admin;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void del(){

        stmt.setSetName("admins");
        RecordSet rs = aerospikeClient.query(null, stmt);

        List<Admin> studentList = StreamSupport.stream(rs.spliterator(), false).map( rec ->{
            Map<String, Object> bins = rec.record.bins;
            aerospikeClient.delete(writePolicy,rec.key);
            Admin stu = new Admin((Long) bins.get("PK"), (String) bins.get("name"));
            return stu;
        }).collect(Collectors.toList());

    }
    public Admin getAdminByName(String username) {

        stmt.setSetName("admins");
        RecordSet rs = aerospikeClient.query(null, stmt);

        Optional<KeyRecord> admOptional = StreamSupport.stream(rs.spliterator(), true).parallel().filter(
                rec -> username.equals((String) (rec.record.bins.get("name"))))
                .findFirst();

        System.out.println(admOptional.get());
        if (admOptional.isPresent()){
            Map<String, Object> adminRec = admOptional.get().record.bins;
            Admin admin = new Admin((Long) adminRec.get("PK"), (String) adminRec.get("name"), (String) adminRec.get("password"));
            return  admin;
        }
        return  null;
    }


    //Courses **************
    public  List<Course>  getAllCourses() {

        List<Course> courseList = mapper.scan(Course.class);
//        stmt.setSetName("courses");
//        RecordSet rs = aerospikeClient.query(null, stmt);
//
//        List<Course> courseList = StreamSupport.stream(rs.spliterator(), false).map( rec ->{
//            Map<String, Object> bins = rec.record.bins;
////            aerospikeClient.delete(writePolicy,rec.key);
//            Course course = new Course((Long) bins.get("PK"), (String) bins.get("name"));
////            System.err.println(stu);
//            return course;
//        }).collect(Collectors.toList());
//        System.out.println(stuList.toString());
        return courseList;
    }

    public Course insertCourse(Course course) {

        stmt.setSetName("courses");
        long id = this.getNextId(stmt);
        System.out.println("Size: " + id);
        course.setId(id);
        mapper.save(course);
//        Key key = new Key(NAMESPACE, "courses", course.getId());
//        Bin PK = new Bin("PK", course.getId());
//        Bin name = new Bin("name", course.getName());
//        aerospikeClient.put(writePolicy, key, PK, name);
        System.out.println("Course Created!");
        return course;
    }

    public Course getCourseById(long id){
        try{
           Course course= mapper.read(Course.class,id);
//            Key key = new Key(NAMESPACE,"courses",id);
//            Record record = aerospikeClient.get(null,key);
//            System.out.println(record.bins.get("PK"));
//            Course course = new Course((Long) record.bins.get("PK"), (String) record.bins.get("name"));
            System.out.println(course);
            return course;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Student registerCourse(long stuId, long courseId) {
        Student student = mapper.read(Student.class,stuId);
        Course course = mapper.read(Course.class,courseId);
        student.addNewCourse(course);
        if (student.isCourseRegistered(course)){
            course.incrementNumOfStu();
            mapper.save(course);
        }
        mapper.save(student);
        System.out.println("course registered !");

        return student;
    }

    public Student removeRegCourse(long stuId, long courseId) {
        Student student = mapper.read(Student.class,stuId);
        Course course = mapper.read(Course.class,courseId);
        student.removeCourse(course);
        course.decrementNumOfStu();
        mapper.save(student);
        mapper.save(course);
        System.out.println("registered course removed !");
        return student;
    }

    public void removeCourse(long id) {
        Course course = mapper.read(Course.class,id);
        mapper.delete(course);
        System.out.println("Course removed !");
    }
}
