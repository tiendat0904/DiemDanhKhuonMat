package com.example.attendance.Model;

import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Shift {
    private String shiftID;
    private String shiftName;

    public String getShiftID() {
        return shiftID;
    }

    public void setShiftID(String shiftID) {
        this.shiftID = shiftID;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getShiftStart() {
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:s");
//        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Format f = new SimpleDateFormat("HH:mm:ss'Z'");
        Date date = null;
        try {
            date = simpleDateFormat.parse(shiftStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        LocalDate date = LocalDate.parse(shiftStart.toString(), inputFormatter);
        String formattedDate = f.format(date);
        return formattedDate;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public String getShiftEnd() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:s");
//        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Format f = new SimpleDateFormat("HH:mm:ss'Z'");
        Date date = null;
        try {
            date = simpleDateFormat.parse(shiftEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        LocalDate date = LocalDate.parse(shiftStart.toString(), inputFormatter);
        String formattedDate = f.format(date);
        return formattedDate;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public Shift(String shiftID, String shiftName, String shiftStart, String shiftEnd) {
        this.shiftID = shiftID;
        this.shiftName = shiftName;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
    }

    private String shiftStart;
    private String shiftEnd;


}
