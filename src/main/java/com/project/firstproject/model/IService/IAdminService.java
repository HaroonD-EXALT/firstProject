package com.project.firstproject.model.IService;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.domain.Admin;
import com.project.firstproject.model.LoginModel;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IAdminService {
    @WebMethod()
    public Admin[] getAllAdmins() throws AerospikeException;

    @WebMethod
    public Admin getAdminById(long id) throws Exception;

    @WebMethod
    public boolean logIn(LoginModel login) throws Exception;

    @WebMethod
    public Admin addAdmin(Admin admin) throws AerospikeException;

    @WebMethod
    public Admin getAdminByName(String username) throws Exception;

    @WebMethod
    Admin removeAdmin(long id) throws AerospikeException,Exception;
}
