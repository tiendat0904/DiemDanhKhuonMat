package com.example.attendance;

import java.util.Date;

public class SubjectClass {
    private int SubjectClassID;
    private String SubjectClassName;
    private Date StartDate;
    private Date EndDate;
    private int status;
    private int SubjectID;

    public SubjectClass(int subjectClassID, String subjectClassName, int status, int subjectID) {
        this.SubjectClassID = subjectClassID;
        this.SubjectClassName = subjectClassName;
        this.status = status;
        this.SubjectID = subjectID;
    }

    public int getSubjectClassID() {
        return SubjectClassID;
    }

    public void setSubjectClassID(int subjectClassID) {
        SubjectClassID = subjectClassID;
    }

    public String getSubjectClassName() {
        return SubjectClassName;
    }

    public void setSubjectClassName(String subjectClassName) {
        SubjectClassName = subjectClassName;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(int subjectID) {
        SubjectID = subjectID;
    }
}
