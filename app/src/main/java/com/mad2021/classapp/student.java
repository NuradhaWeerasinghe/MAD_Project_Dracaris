package com.mad2021.classapp;

public class student {

    //Attributes
    public String name,age,gender,school,email,password;

    // Default Constructor
    public student() {
    }

    // Overloaded Constructor

    public student(String name, String age, String gender, String school, String email, String password) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.school = school;
        this.email = email;
        this.password = password;
    }
}

