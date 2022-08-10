package com.project.firstproject.model.dao.impl;

import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.query.ResultSet;
import com.aerospike.client.query.Statement;
import com.project.firstproject.configuration.AerospikeDatabase;
import com.project.firstproject.domain.Admin;
import com.project.firstproject.domain.Student;
import com.project.firstproject.model.dao.Dao;

import java.util.List;
import java.util.Map;

public class AdminDao implements Dao<Admin> {
    private AerospikeDatabase database = AerospikeDatabase.getInstance();

    @Override
    public Admin save(Admin item) {
        long id = this.getNextId();
        item.setId(id);
        item.setRole("admin");
        System.out.println(item);
        //save object in the database, if it fails throws AerospikeException, and it will be caught in the resource class
        database.getMapper().save(item);
        System.out.println("Admin Created");
        return item;
    }

    @Override
    public Admin getById(long id) throws Exception {
        Key key = new Key(database.getNAMESPACE(), "admins", id);
        Record record = database.getAerospikeClient().get(null, key);
        if (record == null) {
            throw new Exception("admin not found");
        }
        Admin admin = new Admin((Long) record.bins.get("PK"), (String) record.bins.get("name"), (String) record.bins.get("password"));
        System.out.println(admin);
        return admin;
    }

    public Admin getByName(String name) throws Exception {
        Statement stmt = database.getStmt();
        stmt.setSetName("admins");
        ResultSet rs = database.getAerospikeClient().queryAggregate(null, stmt,
                "get_admin_by_name", "get_by_name",
                Value.get(name));
        if (rs.next()) {
            System.out.println(rs);
            System.out.println(rs.getObject());
            Map<String,Object> map = (Map<String, Object>) rs.getObject();
            Admin result = new Admin((Long) map.get("PK"), (String) map.get("name"), (String) map.get("password"));
            System.out.println(result);
            return result;
        }

        throw new Exception("admin not found,wrong username");

    }

    @Override
    public List<Admin> getAll() {
        List<Admin> adminList = database.getMapper().scan(Admin.class);
        System.out.println("***********");
        for (Admin a: adminList
        ) {
            System.out.println(a);

        }
        System.out.println("***********");
        return adminList;
    }

    @Override
    public Admin update(long id, Admin item) {
        return null;
    }

    @Override
    public Admin delete(long id) throws Exception {
        Admin admin = database.getMapper().read(Admin.class, id);
        //there is any student registered in the course and can be safely deleted
        if (admin == null) {
            throw new Exception("Admin not exists!");
        }

        database.getMapper().delete(admin);
        System.out.println("Admin removed !");
        return admin;
    }

    private long getNextId() {
        return database.getMapper().scan(Admin.class).size() +1;
    }

}
