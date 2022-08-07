package com.project.firstproject.resources;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.model.Admin;
import com.project.firstproject.model.LoginModel;
import com.project.firstproject.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;


//@Path("/admins")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
@RestController
@RequestMapping("/admins")
public class AdminResource {

    AdminService adminService = new AdminService();

    @GetMapping
    public List<Admin> getAllAdmins (){
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public ResponseEntity getAdminById(@PathVariable("id") UUID id) {
        try {
            Admin admin = adminService.getAdminById(id);
            return ResponseEntity.ok(admin);
        }catch (AerospikeException ae){
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something wrong try again later ");
        }


    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody  LoginModel login){
        //simple authentication, it should not be like this in real project ^_^
         boolean isAuthenticated = adminService.logIn(login);
         if (isAuthenticated){
             return ResponseEntity.status(HttpStatus.OK).body("successful login") ;
         }
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("username or password is incorrect.");
    }

    @PostMapping
    public ResponseEntity<Object> addNewAdmin(@Valid @RequestBody Admin admin){
        try {
           Admin addAdmin = adminService.addAdmin(admin);
            return ResponseEntity.ok(addAdmin);
        }catch (AerospikeException ae){
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.internalServerError().body("Database Error, something wrong! ");
        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
