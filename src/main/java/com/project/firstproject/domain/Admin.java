package com.project.firstproject.domain;

import com.aerospike.mapper.annotations.AerospikeBin;
import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ComponentScan
@AerospikeRecord(namespace="test", set="admins")
public class Admin{
    @AerospikeKey
    @AerospikeBin(name = "PK")
    private long id;







    @NotNull(message = "name is required, must not be null")
    @NotEmpty(message = "name is required, must not be empty")
    @NotBlank(message = "name is required, must not be blank")
    private String name;

    private String role;

    @NotNull(message = "password is required, must not be null")
    @NotEmpty(message = "password is required, must not be empty")
    @NotBlank(message = "password is required, must not be blank")
    private String password;

    private String privateData;

    public Admin() {
    }

    public Admin(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = "admin";
        this.privateData="privateData";
    }

    public Admin(long id, String name) {
        this.id = id;
        this.name = name;
        this.role = "admin";
        this.privateData="privateData";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getPrivateData() {
        return privateData;
    }

    public void setPrivateData(String privateData) {
        this.privateData = privateData;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", privateData='" + privateData + '\'' +
                '}';
    }
}
