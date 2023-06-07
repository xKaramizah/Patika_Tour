package com.patikatour.Model;

public class Guest extends User{
    public Guest(){

    }
    public Guest(int id, String name, String uname, String pass, String email, String type) {
        super(id, name, uname, pass, email, type);
    }
}
