package com.project.firstproject.soapService;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.model.IService.IAdminService;
import com.project.firstproject.domain.Admin;
import com.project.firstproject.model.LoginModel;
import com.project.firstproject.restServices.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;


@WebService(endpointInterface = "com.project.firstproject.model.IService.IAdminService")
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminService adminService;

    @Override
    public Admin[] getAllAdmins() {
        Admin[] admins = adminService.getAllAdmins();
        return admins;
    }

    @Override
    public Admin getAdminById(long id)  {

        try {
            return adminService.getAdminById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean logIn(LoginModel login) throws Exception {
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
    public Admin getAdminByName(String username) throws Exception {
        return adminService.getAdminByName(username);
    }

    @Override
    public Admin removeAdmin(long id) throws AerospikeException, Exception {
        return adminService.removeAdmin(id);
    }
}
