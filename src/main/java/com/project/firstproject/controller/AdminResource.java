package com.project.firstproject.controller;

import com.aerospike.client.AerospikeException;
import com.project.firstproject.domain.Admin;
import com.project.firstproject.model.LoginModel;
import com.project.firstproject.restServices.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/admins")
public class AdminResource {

    AdminService adminService = new AdminService();

    @GetMapping
    public ResponseEntity<Object> getAllAdmins() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdmins());

        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error! => " + ae.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something wrong try again later => " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getAdminById(@PathVariable("id") long id) {
        try {
            Admin admin = adminService.getAdminById(id);
            return ResponseEntity.ok(admin);
        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something wrong " + e.getMessage() + " try again later");
        }


    }

    @GetMapping("/name/{name}")
    public ResponseEntity getAdminByName(@PathVariable("name") String id) {
        try {
            Admin admin = adminService.getAdminByName(id);
            return ResponseEntity.ok(admin);
        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something wrong " + e.getMessage() + " try again later");
        }


    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginModel login) {
        //simple authentication, it should not be like this in real project ^_^
        boolean isAuthenticated = false;
        try {
            isAuthenticated = adminService.logIn(login);
            if (isAuthenticated) {
                return ResponseEntity.status(HttpStatus.OK).body("successful login");

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("username or password is incorrect.");

            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }

    @PostMapping("/create")
    public ResponseEntity<Object> addNewAdmin(@Valid @RequestBody Admin admin) {
        try {
            Admin addedAdmin = adminService.addAdmin(admin);
            return ResponseEntity.ok(addedAdmin);
        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.internalServerError().body("Database Error," + ae.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAdmin(@PathVariable("id") long id) {
        try {
            Admin admin = adminService.removeAdmin(id);
            return ResponseEntity.ok(admin);

        } catch (AerospikeException ae) {
            System.err.println(ae.getMessage());
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
