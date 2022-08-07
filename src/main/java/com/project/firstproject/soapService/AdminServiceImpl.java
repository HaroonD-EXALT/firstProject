package com.project.firstproject.soapService;

import com.project.firstproject.IServiceRepo.IAdminService;
import com.project.firstproject.database.AerospikeDatabase;
import com.project.firstproject.model.Admin;
import com.project.firstproject.model.LoginModel;
import com.project.firstproject.pojo.PojoAdmin;
import com.project.firstproject.restServices.AdminService;


import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebService(endpointInterface = "com.project.firstproject.IServiceRepo.IAdminService")
public class AdminServiceImpl implements IAdminService {
    private AerospikeDatabase database= AerospikeDatabase.getInstance();
    private AdminService adminService = new AdminService();

    @Override
    public Object getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        PojoAdmin pojoAdmin = new PojoAdmin();

        pojoAdmin.setAdmins(admins);

        return pojoAdmin;
    }

    @Override
    public Admin getAdminById(UUID id) {

        return adminService.getAdminById(id);
    }

    @Override
    public boolean logIn(LoginModel login) {
        Admin admin=  adminService.getAdminByName(login.getUsername());
        if (admin == null) return false;
        if (admin.getPassword().equals(login.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public Admin addAdmin(Admin admin) {
        return adminService.addAdmin(admin);
    }

    @Override
    public Admin getAdminByName(String username) {
        return adminService.getAdminByName(username);
    }
}
