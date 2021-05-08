package com.mad2021.classapp;

import java.util.jar.Attributes;

public class teacher {
    //Attributes
    String name,age,gender,school,email,password;
    
    // Default Constructor
    public teacher() {
    }
    
    // Overloaded Constructor

    public teacher(String name, String age, String gender, String school, String email, String password) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.school = school;
        this.email = email;
        this.password = password;
    }
}
