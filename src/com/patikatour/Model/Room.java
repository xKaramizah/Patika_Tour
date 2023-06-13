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
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result && addPrices(hotelID, type, adultDiscountedPrice, adultPrice, childDiscountedPrice, childPrice);
    }

    private static boolean addPrices(int hotelID, String type, double adultDiscountedPrice, double adultPrice, double childDiscountedPrice, double childPrice) {
        String query = "INSERT INTO price (adult_price, adult_price_dis, child_price, child_price_dis, room_id) VALUES (?,?,?,?,?)";
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setDouble(1, adultPrice);
            ps.setDouble(2, adultDiscountedPrice);
            ps.setDouble(3, childPrice);
            ps.setDouble(4, childDiscountedPrice);
            ps.setDouble(5, getFetch(hotelID, type).getId());
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM room, price " +
                "USING room " +
                "JOIN price ON room.id = price.room_id " +
                "WHERE room.id = ?";
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setInt(1, id);
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean update(int id, String type, byte bedNumber, boolean tv, boolean minibar, boolean gameConsole, boolean vault, boolean projection, double squareMeter,
                                 int hotelID, byte stock, double adultDiscountedPrice, double adultPrice, double childDiscountedPrice, double childPrice) {
        String query = "UPDATE room SET type = ?, bed_number = ?, tv = ?, minibar = ?, game_console= ?, vault = ?, projection = ?,square_meter = ?, hotel_id =?, stock =? WHERE id = ?";
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
            ps.setInt(11, id);
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result && updatePrices(hotelID, type, adultPrice, adultDiscountedPrice, childPrice, childDiscountedPrice);
    }

    private static boolean updatePrices(int hotelID, String type, double adultPrice, double adultDiscountedPrice, double childPrice, double childDiscountedPrice) {
        String query = "UPDATE price SET adult_price = ?, adult_price_dis = ?, child_price =?, child_price_dis = ? WHERE room_id = ?";
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setDouble(1, adultPrice);
            ps.setDouble(2, adultDiscountedPrice);
            ps.setDouble(3, childPrice);
            ps.setDouble(4, childDiscountedPrice);
            ps.setInt(5, Room.getFetch(hotelID, type).getId());
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static Room getFetch(int hotelID, String type) {
        Room obj = null;
        String query = "SELECT * FROM room WHERE hotel_id = ? AND type = ?";
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setInt(1, hotelID);
            ps.setString(2, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static Room getFetch(int id) {
        Room obj = null;
        String query = "SELECT * FROM room WHERE id = ?";
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static ArrayList<Room> searchList(String data, int personCount) {
        ArrayList<Room> roomList = new ArrayList<>();
        Room obj;
        String query = "SELECT * FROM room " +
                "INNER JOIN hotel ON room.hotel_id = hotel.id " +
                "WHERE (hotel.address LIKE '%{{address}}%' OR hotel.region LIKE '%{{region}}%' OR hotel.city LIKE '%{{city}}%') " +
                "AND bed_number >= {{personCount}} " +
                "AND stock > 0 ";

        query = query.replace("{{address}}", data);
        query = query.replace("{{region}}", data);
        query = query.replace("{{city}}", data);
        query = query.replace("{{personCount}}", String.valueOf(personCount));

        try {
            Statement st = DBConnector.getConnect().createStatement();
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
                roomList.add(obj);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomList;
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

}

