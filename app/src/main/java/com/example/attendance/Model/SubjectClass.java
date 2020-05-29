package com.example.attendance.Model;

import android.icu.text.TimeZoneFormat;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class SubjectClass {
    private int subjectClassID;
    private String subjectClassName;
    private String startDate;
    private String endDate;
    private int status;
    private int subjectID;

    public SubjectClass(int subjectClassID, String subjectClassName, int status, int subjectID) {
        this.subjectClassID = subjectClassID;
        this.subjectClassName = subjectClassName;
        this.status = status;
        this.subjectID = subjectID;
    }



    public int getSubjectClassID() {
        return subjectClassID;
    }

    public String getSubjectClassName() {
        return subjectClassName;
    }

    public String getStartDate() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(startDate.toString(), inputFormatter);
        String formattedDate = outputFormatter.format(date);
        return formattedDate;
    }

    public String getEndDate() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(endDate.toString(), inputFormatter);
        String formattedDate = outputFormatter.format(date);
        return formattedDate;
    }

    public int getStatus() {
        return status;
    }

    public int getSubjectID() {
        return subjectID;
    }
}
