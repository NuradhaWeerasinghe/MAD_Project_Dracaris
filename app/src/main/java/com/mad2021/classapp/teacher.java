package com.mad2021.classapp;

import java.util.jar.Attributes;

public class teacher {
    //Attributes
    private String name;
    private String birthday;
    private String gender;
    private String school;
    private String email;
    private String password;
    private String phone;
    
    // Default Constructor
    public teacher() {
    }
    
    // Overloaded Constructor


    public teacher(String name, String birthday, String gender, String school, String email, String password, String phone) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.school = school;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
