package com.patikatour.Model;

import com.patikatour.Helper.DBConnector;

import java.sql.*;
import java.util.ArrayList;

public class Room {
    private int id;
    private String type;
    private byte bedNumber;
    private boolean tv;
    private boolean minibar;
    private boolean gameConsole;
    private boolean vault;
    private boolean projection;
    private double squareMeter;
    private int hotelID;
    private byte stock;
    private double adultDiscountedPrice;
    private double adultPrice;
    private double childDiscountedPrice;
    private double childPrice;

    public Room() {
    }

    public Room(int id, String type, byte bedNumber, boolean tv, boolean minibar, boolean gameConsole, boolean vault, boolean projection, double squareMeter, int hotelID, byte stock, double adultDiscountedPrice, double adultPrice, double childDiscountedPrice, double childPrice) {
        this.id = id;
        this.type = type;
        this.bedNumber = bedNumber;
        this.tv = tv;
        this.minibar = minibar;
        this.gameConsole = gameConsole;
        this.vault = vault;
        this.projection = projection;
        this.squareMeter = squareMeter;
        this.hotelID = hotelID;
        this.stock = stock;
        this.adultDiscountedPrice = adultDiscountedPrice;
        this.adultPrice = adultPrice;
        this.childDiscountedPrice = childDiscountedPrice;
        this.childPrice = childPrice;
    }

    public static ArrayList<Room> getList() {
        ArrayList<Room> rooms = new ArrayList<>();
        Room obj;
        String query = "SELECT * FROM room";
        try {
            Connection conn = DBConnector.getConnect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Room();
                obj.setId(rs.getInt("id"));
                obj.setType(rs.getString("type"));
                obj.setBedNumber(rs.getByte("bed_number"));
                obj.setTv(rs.getBoolean("tv"));
                obj.setMinibar(rs.getBoolean("minibar"));
                obj.setGameConsole(rs.getBoolean("game_console"));
                obj.setVault(rs.getBoolean("vault"));
                obj.setProjection(rs.getBoolean("projection"));
                obj.setSquareMeter(rs.getDouble("square_meter"));
                obj.setHotelID(rs.getInt("hotel_id"));
                obj.setStock(rs.getByte("stock"));
                rooms.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    public static boolean add(String type, byte bedNumber, boolean tv, boolean minibar, boolean gameConsole, boolean vault, boolean projection, double squareMeter, int hotelID, byte stock, double adultDiscountedPrice, double adultPrice, double childDiscountedPrice, double childPrice) {
        String query = "INSERT INTO room (type, bed_number, tv, minibar, game_console, vault, projection, square_meter, hotel_id, stock) VALUES (?,?,?,?,?,?,?,?,?,?)";
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setString(1, type);
            ps.setByte(2, bedNumber);
            ps.setBoolean(3, tv);
            ps.setBoolean(4, minibar);
            ps.setBoolean(5, gameConsole);
            ps.setBoolean(6, vault);
            ps.setBoolean(7, projection);
            ps.setDouble(8, squareMeter);
            ps.setInt(9, hotelID);
            ps.setByte(10, stock);
            result = ps.executeUpdate() != -1;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(byte bedNumber) {
        this.bedNumber = bedNumber;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isMinibar() {
        return minibar;
    }

    public void setMinibar(boolean minibar) {
        this.minibar = minibar;
    }

    public boolean isGameConsole() {
        return gameConsole;
    }

    public void setGameConsole(boolean gameConsole) {
        this.gameConsole = gameConsole;
    }

    public boolean isVault() {
        return vault;
    }

    public void setVault(boolean vault) {
        this.vault = vault;
    }

    public boolean isProjection() {
        return projection;
    }

    public void setProjection(boolean projection) {
        this.projection = projection;
    }

    public double getSquareMeter() {
        return squareMeter;
    }

    public void setSquareMeter(double squareMeter) {
        this.squareMeter = squareMeter;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public byte getStock() {
        return stock;
    }

    public void setStock(byte stock) {
        this.stock = stock;
    }

    public double getAdultDiscountedPrice() {
        return adultDiscountedPrice;
    }

    public void setAdultDiscountedPrice(double adultDiscountedPrice) {
        this.adultDiscountedPrice = adultDiscountedPrice;
    }

    public double getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public double getChildDiscountedPrice() {
        return childDiscountedPrice;
    }

    public void setChildDiscountedPrice(double childDiscountedPrice) {
        this.childDiscountedPrice = childDiscountedPrice;
    }

    public double getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(double childPrice) {
        this.childPrice = childPrice;
    }
}

