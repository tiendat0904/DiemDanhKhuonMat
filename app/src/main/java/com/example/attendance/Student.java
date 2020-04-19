package com.example.attendance;

public class Student {
    private String Msv,HoTen,Monhoc;
    private boolean isDiHoc;

    public Student(String msv, String hoTen, String monhoc) {
        Msv = msv;
        HoTen = hoTen;
        Monhoc = monhoc;
    }

    public String getMsv() {
        return Msv;
    }

    public void setMsv(String msv) {
        Msv = msv;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getMonhoc() {
        return Monhoc;
    }

    public void setMonhoc(String monhoc) {
        Monhoc = monhoc;
    }

    public boolean isDiHoc() {
        return isDiHoc;
    }

    public void setDiHoc(boolean diHoc) {
        isDiHoc = diHoc;
    }
}
