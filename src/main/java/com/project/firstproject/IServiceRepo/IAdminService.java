package com.project.firstproject.IServiceRepo;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.model.Admin;
import com.project.firstproject.model.LoginModel;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.UUID;
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IAdminService {
    @WebMethod()
    public Object getAllAdmins() throws AerospikeException;
    @WebMethod()
    public Admin getAdminById(@WebParam UUID id) throws AerospikeException;
    @WebMethod
    public boolean logIn(LoginModel login);
    @WebMethod
    public Admin addAdmin(Admin admin) throws AerospikeException;
    @WebMethod
    public Admin getAdminByName(String username);
}
