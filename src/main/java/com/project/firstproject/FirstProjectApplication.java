package com.project.firstproject;

import com.project.firstproject.domain.Admin;
import com.project.firstproject.model.dto.AdminDto;
import com.project.firstproject.soapService.AdminServiceImpl;
import com.project.firstproject.soapService.CourseServiceImpl;
import com.project.firstproject.soapService.StudentServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.xml.ws.Endpoint;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class FirstProjectApplication {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        return mapper;
    }
    public static void main(String[] args) {


        Thread restServiceThread = new Thread(new Runnable() {
            @Override
            public void run() {
                SpringApplication.run(FirstProjectApplication.class, args);

            }
        });
        restServiceThread.start();

        Thread soapServiceThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Soap!!");
                Endpoint.publish("http://localhost:8888/ws/api/admins", new AdminServiceImpl());
                Endpoint.publish("http://localhost:8888/ws/api/students", new StudentServiceImpl());
                Endpoint.publish("http://localhost:8888/ws/api/courses", new CourseServiceImpl());

            }
        });
        soapServiceThread.start();

    }
}
