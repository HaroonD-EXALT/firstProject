package com.project.firstproject.services;

import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Admin;
import com.project.firstproject.model.LoginModel;

import java.util.List;

public class AdminService {

    private AerospikeDatabase database= AerospikeDatabase.getInstance();

    public List<Admin> getAllAdmins() {
        return database.getAllAdmins();
    }

    public Admin getAdminById(long id) {
        return database.getAdminById(id);
    }

    public boolean logIn(LoginModel login) {
      Admin admin=  database.getAdminByName(login.getUsername());
      if (admin == null) return false;
      if (admin.getPassword().equals(login.getPassword())){
          return true;
      }
      return false;
    }

    public Admin addAdmin(Admin admin) {
        admin.setRole("admin");
        return database.insertAdmin(admin);
    }
}
