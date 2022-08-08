package com.project.firstproject.IServiceRepo;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.model.Admin;
import com.project.firstproject.model.LoginModel;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IAdminService {
    @WebMethod()
    public Admin[] getAllAdmins() throws AerospikeException;

    @WebMethod
    public Admin getAdminById(long id) throws Exception;

    @WebMethod
    public boolean logIn(LoginModel login);

    @WebMethod
    public Admin addAdmin(Admin admin) throws AerospikeException;

    @WebMethod
    public Admin getAdminByName(String username);
}
