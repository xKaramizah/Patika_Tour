package com.patikatour.Model;

import com.patikatour.Helper.DBConnector;
import com.patikatour.Helper.Helper;

import java.sql.*;
import java.util.ArrayList;

public class Hotel {
    private int id;
    private String name;
    private String address;
    private String city;
    private String region;
    private String phone;
    private String email;
    private int star;
    private String features;
    private String serviceType;
    private Date winterStart;
    private Date winterEnd;

    public Hotel() {
    }

    public Hotel(int id, String name, String address, String city, String region, String phone, String email, int star, String features, String serviceType, Date winterStart, Date winterEnd) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.region = region;
        this.phone = phone;
        this.email = email;
        this.star = star;
        this.features = features;
        this.serviceType = serviceType;
        this.winterStart = winterStart;
        this.winterEnd = winterEnd;
    }

    public static boolean add(String name, String address, String city, String region, String phone, String email, int star, String features, String service_type, Date winterStart, Date winterEnd) {
        String query = "INSERT INTO hotel (name, address, city, region, phone, email, star, features, service_type) VALUES (?,?,?,?,?,?,?,?,?)";
        boolean result;
        if (getFetch("SELECT * FROM hotel WHERE name = '" + name + "'") != null) {
            Helper.showMessageDialog("Bu otel daha önceden eklenmiş");
            result = false;
        } else {
            try {
                PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, address);
                ps.setString(3, city);
                ps.setString(4, region);
                ps.setString(5, phone);
                ps.setString(6, email);
                ps.setInt(7, star);
                ps.setString(8, features);
                ps.setString(9, service_type);
                result = ps.executeUpdate() != -1;
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result && addWinterDates(name, winterStart, winterEnd);
    }

    private static boolean addWinterDates(String hotelName, Date winterStart, Date winterEnd) {
        String query = "INSERT INTO period (start_date, end_date, hotel_id) VALUES (?,?,?)";
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setDate(1, winterStart);
            ps.setDate(2, winterEnd);
            ps.setInt(3, getFetch("SELECT * FROM hotel WHERE name = '" + hotelName + "'").getId());
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean update(int id, String name, String address, String city, String region, String phone, String email, int star, String features, String serviceType, Date winterStart, Date winterEnd) {
        String query = "UPDATE hotel " +
                "SET name=?, address=?, city=?, region=?, phone=?, email=?, star=?, features=?, service_type=? " +
                "WHERE id = ?";
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, city);
            ps.setString(4, region);
            ps.setString(5, phone);
            ps.setString(6, email);
            ps.setInt(7, star);
            ps.setString(8, features);
            ps.setString(9, serviceType);
            ps.setInt(10, id);
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result && updateDates(id, winterStart, winterEnd);
    }

    private static boolean updateDates(int id, Date winterStart, Date winterEnd) {
        String query = "UPDATE period SET start_date =?, end_date=? WHERE hotel_id =?";
        boolean result;
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setDate(1,winterStart);
            ps.setDate(2,winterEnd);
            ps.setInt(3,id);
            result = ps.executeUpdate() != -1;
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean delete(int id) {
        boolean result;
        String query = "DELETE FROM hotel, room, price " +
                "USING hotel " +
                "JOIN room ON hotel.id = room.hotel_id " +
                "JOIN price ON room.id = price.room_id " +
                " WHERE hotel.id = ?";
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

    public static Hotel getFetch(String query) {
        Hotel obj = null;
        try {
            Connection conn = DBConnector.getConnect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                obj = new Hotel();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setAddress(rs.getString("address"));
                obj.setCity(rs.getString("city"));
                obj.setRegion(rs.getString("region"));
                obj.setPhone(rs.getString("phone"));
                obj.setEmail(rs.getString("email"));
                obj.setStar(rs.getInt("star"));
                obj.setFeatures(rs.getString("features"));
                obj.setServiceType(rs.getString("service_type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static ArrayList<Hotel> getList() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        Hotel obj;
        Period periodObj;
        String query = "SELECT * FROM hotel";
        try {
            Connection conn = DBConnector.getConnect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Hotel();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setAddress(rs.getString("address"));
                obj.setCity(rs.getString("city"));
                obj.setRegion(rs.getString("region"));
                obj.setPhone(rs.getString("phone"));
                obj.setEmail(rs.getString("email"));
                obj.setStar(rs.getInt("star"));
                obj.setFeatures(rs.getString("features"));
                obj.setServiceType(rs.getString("service_type"));
                periodObj = Period.getFetch("SELECT * FROM period WHERE hotel_id = " + rs.getInt("id"));
                if (periodObj != null) {
                    obj.setWinterStart((Date) periodObj.getStartDate());
                    obj.setWinterEnd((Date) periodObj.getEndDate());
                }
                hotels.add(obj);
            }
            conn.close();
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotels;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getWinterStart() {
        return winterStart;
    }

    public void setWinterStart(Date winterStart) {
        this.winterStart = winterStart;
    }

    public Date getWinterEnd() {
        return winterEnd;
    }

    public void setWinterEnd(Date winterEnd) {
        this.winterEnd = winterEnd;
    }
}