package com.example.attendance.Model;

import android.content.Intent;

import com.google.api.client.util.DateTime;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event_Post {
    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    private Integer eventID;
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
