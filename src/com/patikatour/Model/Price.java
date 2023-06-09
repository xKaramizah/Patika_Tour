package com.patikatour.Model;

import com.patikatour.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Price {
    private int id;
    private double adultPrice;
    private double adultPriceDis;
    private double childPrice;
    private double childPriceDis;
    private int roomID;

    public Price(int id, double adultPrice, double adultPriceDis, double childPrice, double childPriceDis, int roomID) {
        this.id = id;
        this.adultPrice = adultPrice;
        this.adultPriceDis = adultPriceDis;
        this.childPrice = childPrice;
        this.childPriceDis = childPriceDis;
        this.roomID = roomID;
    }

    public static Price getFetch(int roomID) {
        String query = "SELECT * FROM price WHERE room_id = ?";
        Price obj = null;
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setInt(1, roomID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Price(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getInt(6));
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

    public double getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public double getAdultPriceDis() {
        return adultPriceDis;
    }

    public void setAdultPriceDis(double adultPriceDis) {
        this.adultPriceDis = adultPriceDis;
    }

    public double getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(double childPrice) {
        this.childPrice = childPrice;
    }

    public double getChildPriceDis() {
        return childPriceDis;
    }

    public void setChildPriceDis(double childPriceDis) {
        this.childPriceDis = childPriceDis;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
}
