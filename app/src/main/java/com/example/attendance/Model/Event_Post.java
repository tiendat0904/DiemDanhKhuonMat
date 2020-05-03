package com.example.attendance.Model;

import com.google.api.client.util.DateTime;

public class Event_Post {
    private int shiftID;
    private int subjectClassID;
    private DateTime dateTime;

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public int getSubjectClassID() {
        return subjectClassID;
    }

    public void setSubjectClassID(int subjectClassID) {
        this.subjectClassID = subjectClassID;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Event_Post(int shiftID, int subjectClassID, DateTime dateTime) {
        this.shiftID = shiftID;
        this.subjectClassID = subjectClassID;
        this.dateTime = dateTime;
    }
}
