package com.patikatour.Model;

import com.patikatour.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Booking {
    private int id;
    private java.sql.Date start_date;
    private java.sql.Date end_date;
    private String name;
    private String phone;
    private String email;
    private String note;
    private int room_id;

    public Booking(int id, java.sql.Date start_date, java.sql.Date end_date, String name, String phone, String email, String note, Room room) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.note = note;
        this.room_id = room.getId();
    }

    public static boolean add(java.sql.Date start_date, java.sql.Date end_date, String name, String phone, String email, String note, Room room) {
        String query = "INSERT INTO booking (start_date, end_date, name, phone, email, note, room_id) VALUES (?,?,?,?,?,?,?)";
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setDate(1, start_date);
            ps.setDate(2, end_date);
            ps.setString(3, name);
            ps.setString(4, phone);
            ps.setString(5, email);
            ps.setString(6, note);
            ps.setInt(7, room.getId());
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(java.sql.Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(java.sql.Date end_date) {
        this.end_date = end_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
}
