package com.example.attendance.Model;

import java.util.ArrayList;

public class AttendanceDetail {
    public String eventID;

    public String getEventID() {
        return eventID;
    }

    public String getSubjectClassID() {
        return subjectClassID;
    }

    public boolean isAttend() {
        return isAttend;
    }

    public String subjectClassID;

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public ArrayList<Student> studentList;
    public boolean isAttend;
}
