package com.mad2021.classapp;

public class ClassData {
    String userId ,className,subject,teacher,ins,des;

    public ClassData() {
    }

    public ClassData(String userId, String className, String subject, String teacher, String ins, String des) {
        this.userId = userId;
        this.className = className;
        this.subject = subject;
        this.teacher = teacher;
        this.ins = ins;
        this.des = des;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getIns() {
        return ins;
    }

    public void setIns(String ins) {
        this.ins = ins;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
