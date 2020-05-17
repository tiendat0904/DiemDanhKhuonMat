package com.example.attendance.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDetail {
    @SerializedName("eventID")
    @Expose
    private Integer eventID;
    @SerializedName("studentList")
    @Expose
    private List<Student> studentList = null;
    @SerializedName("subjectClassID")
    @Expose
    private Integer subjectClassID;

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Integer getSubjectClassID() {
        return subjectClassID;
    }

    public void setSubjectClassID(Integer subjectClassID) {
        this.subjectClassID = subjectClassID;
    }
}

