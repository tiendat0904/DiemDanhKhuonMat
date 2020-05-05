package com.example.attendance.Model;

import java.io.Serializable;

public class StudentDTO implements Serializable {
    private String studentID;

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setAttended(boolean attended) {
        isAttended = attended;
    }

    public String getStudentID() {
        return studentID;
    }

    public boolean isAttended() {
        return isAttended;
    }

    private boolean isAttended;
}
