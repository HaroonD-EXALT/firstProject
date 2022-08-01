package com.project.firstproject.resources;

import com.project.firstproject.model.Admin;
import com.project.firstproject.model.LoginModel;
import com.project.firstproject.services.AdminService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/admins")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {
    AdminService adminService = new AdminService();

    @GET
    public List<Admin> getAllAdmins (){
        return adminService.getAllAdmins();
    }
    @GET
    @Path("/{id}")
    public Admin getAdminById(@PathParam("id") long id) {
        return adminService.getAdminById(id);
    }

    @POST
    @Path("/login")
    public Response login(LoginModel login){
         boolean isAuthenticated = adminService.logIn(login);
         if (isAuthenticated){
             return Response.ok("successful login").build() ;
         }
         return Response.status(Response.Status.UNAUTHORIZED).entity("username or password is incorrect.").build();
    }

    @POST
    public Admin addNewAdmin(Admin admin){
      return  adminService.addAdmin(admin);
    }

}
