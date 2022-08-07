package com.project.firstproject;

import com.aerospike.client.AerospikeClient;
import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Admin;
import com.project.firstproject.model.Student;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        AerospikeDatabase database = AerospikeDatabase.getInstance();

//        Student s = new Student(0,"Haroon Dweikat");
//
//        // Hello
//        out.println(s);
//        database.insertStudent(s);

//        database.creatNewAdmin(new Admin(0,"Hadi","1234"));
//        database.getAllStudent();
//        database.del();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}