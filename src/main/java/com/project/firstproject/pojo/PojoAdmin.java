package com.project.firstproject.pojo;


import com.project.firstproject.model.Admin;

import java.util.ArrayList;
import java.util.List;

public class PojoAdmin {
    private ArrayList<Admin> admins ;
    public List<Admin> getAdmins() {
        return admins;
    }


    public void setAdmins(List admins) {
        this.admins =new ArrayList<>(admins) ;

    }
}
