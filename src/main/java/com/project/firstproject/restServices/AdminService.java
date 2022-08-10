package com.project.firstproject.restServices;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.model.IService.IAdminService;
import com.project.firstproject.domain.Admin;
import com.project.firstproject.model.LoginModel;
import com.project.firstproject.model.dao.impl.AdminDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {



    private AdminDao adminDao = new AdminDao();


    @Override
    public Admin[] getAllAdmins() throws AerospikeException {
        List<Admin> admins = adminDao.getAll();

        return admins.toArray(new Admin[admins.size()]);

    }

    @Override
    public Admin getAdminById(long id) throws AerospikeException, Exception {
        return adminDao.getById(id);
    }

    @Override
    public boolean logIn(LoginModel login) throws Exception {
        Admin admin = adminDao.getByName(login.getUsername());
        //admin is not exists
        if (admin == null){
            return false;
        }
        if (admin.getPassword().equals(login.getPassword())) {
            return true;
        }else{
            throw new Exception("admin not found,wrong password");
        }


    }

    @Override
    public Admin addAdmin(Admin admin) throws AerospikeException {
        return adminDao.save(admin);
    }



    @Override
    public Admin getAdminByName(String username) throws Exception {
        return adminDao.getByName(username);
    }

    @Override
    public Admin removeAdmin(long id) throws AerospikeException,Exception {
        return adminDao.delete(id);

    }



}
