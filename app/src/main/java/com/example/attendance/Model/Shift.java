package com.example.attendance.Model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(shiftStart.toString(), inputFormatter);
        String formattedDate = outputFormatter.format(date);
        return formattedDate;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public String getShiftEnd() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(shiftEnd.toString(), inputFormatter);
        String formattedDate = outputFormatter.format(date);
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
