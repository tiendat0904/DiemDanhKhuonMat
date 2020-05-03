package com.example.attendance.Model;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event_Details {
    private String subjectClassName;
    private String dateTime;
    private String shiftName;
    private String eventID;
    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }



    public Event_Details(String subjectClassName, String dateTime, String shiftName,String eventID) {
        this.subjectClassName = subjectClassName;
        this.dateTime = dateTime;
        this.shiftName = shiftName;
        this.eventID = eventID;
    }

    public String getSubjectClassName() {
        return subjectClassName;
    }

    public void setSubjectClassName(String subjectClassName) {
        this.subjectClassName = subjectClassName;
    }

    public String getDateTime() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dateTime.toString(), inputFormatter);
        String formattedDate = outputFormatter.format(date);
        return formattedDate;
    }
    public String getDateTime1()
    {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }




}