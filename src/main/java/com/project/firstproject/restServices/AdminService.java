package com.project.firstproject.restServices;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.query.KeyRecord;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.project.firstproject.IServiceRepo.IAdminService;
import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Admin;
import com.project.firstproject.model.LoginModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        System.out.println(record.bins.get("PK"));
        if (record.bins.get("PK") == null){
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

    @Override
    public Admin getAdminByName(String username) {

        database.getStmt().setSetName("admins");
        RecordSet rs = database.getAerospikeClient().query(null, database.getStmt());
        //iterate over record set and find first occurred of username and return it
        //else return null
        Optional<KeyRecord> admOptional = StreamSupport.stream(rs.spliterator(), true).parallel().filter(
                        rec -> username.equals((String) (rec.record.bins.get("name"))))
                .findFirst();

        System.out.println(admOptional.get());
        if (admOptional.isPresent()) {
            Map<String, Object> adminRec = admOptional.get().record.bins;
            Admin admin = new Admin((Long) adminRec.get("PK"), (String) adminRec.get("name"), (String) adminRec.get("password"));
            return admin;
        }
        return null;
    }

    private long getNextId(Statement stat) {
        RecordSet rs = database.getAerospikeClient().query(null, stat);
        long id = StreamSupport.stream(rs.spliterator(), false).collect(Collectors.toList()).size();
        return id + 1;
    }


}
