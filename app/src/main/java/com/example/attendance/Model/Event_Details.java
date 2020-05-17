package com.example.attendance.Model;


import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Event_Details {
    private String subjectClassName;
    private Integer subjectClassID;
    private String dateTime;

    public int getStatus() {
        return status;
    }

    private int status;
    public Integer getSubjectClassID() {
        return subjectClassID;
    }

    private String shiftName;
    private String eventID;
    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }



    public Event_Details(String subjectClassName, String dateTime, String shiftName,String eventID, Integer subjectClassID, int status) {
        this.subjectClassName = subjectClassName;
        this.dateTime = dateTime;
        this.shiftName = shiftName;
        this.eventID = eventID;
        this.subjectClassID = subjectClassID;
        this.status = status;
    }

    public String getSubjectClassName() {
        return subjectClassName;
    }

    public void setSubjectClassName(String subjectClassName) {
        this.subjectClassName = subjectClassName;
    }

    public String getDateTime() {
//        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
//        LocalDate date = LocalDate.parse(dateTime, inputFormatter);
//        String formattedDate = outputFormatter.format(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:s");
        Format f = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        LocalDate date = LocalDate.parse(shiftStart.toString(), inputFormatter);
        String formattedDate = f.format(date);
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
