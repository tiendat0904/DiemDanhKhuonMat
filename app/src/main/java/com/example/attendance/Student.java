package com.example.attendance;

public class Student {
    private String studentID;
    private String hoTen;
    private String className;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Student(String studentID, String hoTen, String className) {
        this.studentID = studentID;
        this.hoTen = hoTen;
        this.className = className;
    }
}

