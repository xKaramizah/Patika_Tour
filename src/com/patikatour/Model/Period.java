package com.patikatour.Model;

import com.patikatour.Helper.DBConnector;

import java.sql.*;
import java.util.Date;

public class Period {
    private int id;
    private Date startDate;
    private Date endDate;
    private int hotelID;

    public Period(int id, Date startDate, Date endDate, int hotelID) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hotelID = hotelID;
    }

    public static Period getFetch(int hotelID) {
        String query = "SELECT * FROM period WHERE hotel_id = ?";
        Period obj = null;
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setInt(1, hotelID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Period(rs.getInt("id"), rs.getDate("start_date"), rs.getDate("end_date"), rs.getInt("hotel_id"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }
}
