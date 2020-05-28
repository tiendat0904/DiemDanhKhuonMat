package com.example.attendance.Model;

public class Acount {
    String username;
    String password;



    public String getUsername() {
        return username;
    }

    public Acount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
