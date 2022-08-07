package com.project.firstproject.model;

import com.aerospike.mapper.annotations.AerospikeBin;
import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.context.annotation.ComponentScan;


import javax.validation.Constraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@ComponentScan
@AerospikeRecord(namespace="test", set="admins")
public class Admin{
    @AerospikeKey
    @AerospikeBin(name = "PK")
    private UUID id;
    @NotNull(message = "name is required, must not be null")
    @NotEmpty(message = "name is required, must not be empty")
    @NotBlank(message = "name is required, must not be blank")
    private String name;
    private String role;
    @NotNull(message = "password is required, must not be null")
    @NotEmpty(message = "password is required, must not be empty")
    @NotBlank(message = "password is required, must not be blank")
    private String password;

    public Admin() {
    }

    public Admin(UUID id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = "admin";
    }

    public Admin(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.role = "admin";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
