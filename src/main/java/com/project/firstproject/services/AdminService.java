package com.project.firstproject.services;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.query.KeyRecord;
import com.aerospike.client.query.RecordSet;
import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Admin;
import com.project.firstproject.model.LoginModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdminService {

    private AerospikeDatabase database;

    public AdminService() {
        database = AerospikeDatabase.getInstance();
    }

    public List<Admin> getAllAdmins() throws AerospikeException{
        database.getStmt().setSetName("admins");
        RecordSet rs = database.getAerospikeClient().query(null, database.getStmt());
        List<Admin> adminList = StreamSupport.stream(rs.spliterator(), false).map(rec -> {
            Map<String, Object> bins = rec.record.bins;
            Admin admin = new Admin(UUID.fromString(bins.get("PK").toString()), (String) bins.get("name"),(String) bins.get("password"));
            return admin;
        }).collect(Collectors.toList());
        return adminList;

    }

    public Admin getAdminById(UUID id) throws AerospikeException {
        Key key = new Key(database.getNAMESPACE(), "admins", String.valueOf((id)));
        Record record = database.getAerospikeClient().get(null, key);
        System.out.println(record.bins.get("PK"));
        Admin admin = new Admin(UUID.fromString((String) record.bins.get("PK")), (String) record.bins.get("name"), (String) record.bins.get("password"));
        System.out.println(admin);
        return admin;
    }

    public boolean logIn(LoginModel login) {
        Admin admin = getAdminByName(login.getUsername());
        if (admin.getPassword().equals(login.getPassword())) {
            return true;
        }
        //if Admin is not exits or password is wrong
        return false;
    }

    public Admin addAdmin(Admin admin) throws AerospikeException {
        admin.setId(UUID.randomUUID());
        admin.setRole("admin");
        System.out.println(admin);
        //save object in the database, if it fails throws AerospikeException, and it will be caught in the resource class
        database.getMapper().save(admin);
        System.out.println("Admin Created");
        return admin;
    }


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
            Admin admin = new Admin((UUID.fromString( adminRec.get("PK").toString())), (String) adminRec.get("name"), (String) adminRec.get("password"));
            return admin;
        }
        return null;
    }


}
