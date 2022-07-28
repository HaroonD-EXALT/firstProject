package com.project.firstproject.model;

import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@AerospikeRecord(namespace="test", set="admins")
public class Admin{
    @AerospikeKey
    private long id;
    private String name;


    private String role;

    private String password;

    public Admin() {
    }

    public Admin(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = "admin";
    }

    public Admin(long id, String name) {
        this.id = id;
        this.name = name;
        this.role = "admin";
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

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
