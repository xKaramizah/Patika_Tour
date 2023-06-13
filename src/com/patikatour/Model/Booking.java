package com.patikatour.Model;

import com.patikatour.Helper.DBConnector;

import java.sql.*;
import java.util.ArrayList;
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

    public Booking(int id, java.sql.Date start_date, java.sql.Date end_date, String name, String phone, String email, String note, int room_id) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.note = note;
        this.room_id = room_id;
    }

    public static boolean add(java.sql.Date start_date, java.sql.Date end_date, String name, String phone, String email, String note, Room room) {
        if (!isBookable(room, start_date)) {
            return false;
        } else {
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
    }

    private static boolean isBookable(Room room, java.sql.Date enterDate) {
        String query = "SELECT COUNT(*) FROM booking " +
                "WHERE room_id = ? AND start_date <= ? AND end_date >= ?";
        boolean result = true;
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setInt(1, room.getId());
            ps.setDate(2, enterDate);
            ps.setDate(3, enterDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) >= room.getStock()) {
                    result = false;
                    System.out.println(rs.getInt(1) + " oda rezerve");
                }
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static ArrayList<Booking> getList() {
        String query = "SELECT * FROM booking";
        Booking obj;
        ArrayList<Booking> bookings = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Booking(rs.getInt("id"), rs.getDate("start_date"), rs.getDate("end_date"), rs.getString("name"), rs.getString("phone"), rs.getString("email"), rs.getString("note"), rs.getInt("room_id"));
                bookings.add(obj);
            }
            conn.close();
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookings;
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

    public Date getEnd_date() {
        return end_date;
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

    public String getEmail() {
        return email;
    }

    public String getNote() {
        return note;
    }

    public int getRoom_id() {
        return room_id;
    }

}
