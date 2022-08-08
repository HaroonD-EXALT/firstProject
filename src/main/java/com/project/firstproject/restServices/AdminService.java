package com.project.firstproject.restServices;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.query.*;
import com.aerospike.client.task.IndexTask;
import com.project.firstproject.IServiceRepo.IAdminService;
import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Admin;
import com.project.firstproject.model.LoginModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdminService implements IAdminService {

    private AerospikeDatabase database;

    public AdminService() {
        database = AerospikeDatabase.getInstance();
    }

    @Override
    public Admin[] getAllAdmins() throws AerospikeException {
        database.getStmt().setSetName("admins");
        RecordSet rs = database.getAerospikeClient().query(null, database.getStmt());
        List<Admin> adminList = StreamSupport.stream(rs.spliterator(), false).map(rec -> {
            Map<String, Object> bins = rec.record.bins;
            Admin admin = new Admin((Long) bins.get("PK"), (String) bins.get("name"), (String) bins.get("password"));
            return admin;
        }).collect(Collectors.toList());
        return adminList.toArray(new Admin[adminList.size()]);

    }

    @Override
    public Admin getAdminById(long id) throws AerospikeException, Exception {
        Key key = new Key(database.getNAMESPACE(), "admins", id);
        Record record = database.getAerospikeClient().get(null, key);
        if (record == null) {
            throw new Exception("admin not found");
        }
        Admin admin = new Admin((Long) record.bins.get("PK"), (String) record.bins.get("name"), (String) record.bins.get("password"));
        System.out.println(admin);
        return admin;
    }

    @Override
    public boolean logIn(LoginModel login) {
        Admin admin = getAdminByName(login.getUsername());
        if (admin.getPassword().equals(login.getPassword())) {
            return true;
        }
        //if Admin is not exits or password is wrong
        return false;
    }

    @Override
    public Admin addAdmin(Admin admin) throws AerospikeException {
        Statement stmt = database.getStmt();
        stmt.setSetName("admins");
        long id = this.getNextId(stmt);
        admin.setId(id);
        admin.setRole("admin");
        System.out.println(admin);
        //save object in the database, if it fails throws AerospikeException, and it will be caught in the resource class
        database.getMapper().save(admin);
        System.out.println("Admin Created");
        return admin;
    }

    Function<Admin, Boolean> function = admin -> {
        System.out.println(admin.getName());

        return true;
    };

    @Override
    public Admin getAdminByName(String username) {
        final Admin[] finedAdmin = new Admin[1];
        Function<Admin, Boolean> function = admin -> {
            if (admin.getName().equals(username.trim())) {
                finedAdmin[0] = admin;
            }
            return true;
        };
        database.getMapper().find(Admin.class,function);

        return finedAdmin[0];
    }

    private long getNextId(Statement stat) {
        RecordSet rs = database.getAerospikeClient().query(null, stat);
        long id = StreamSupport.stream(rs.spliterator(), false).collect(Collectors.toList()).size();
        return id + 1;
    }


}
