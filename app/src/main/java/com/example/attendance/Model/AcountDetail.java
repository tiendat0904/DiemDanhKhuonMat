package com.example.attendance.Model;

public class AcountDetail {
    Integer id;
    String name,sdt,diachi,username;

    public AcountDetail(Integer id, String name, String sdt, String diachi, String username) {
        this.id = id;
        this.name = name;
        this.sdt = sdt;
        this.diachi = diachi;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
