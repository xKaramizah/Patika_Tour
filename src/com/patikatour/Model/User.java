package com.patikatour.Model;

import com.patikatour.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String name;
    private String uname;
    private String pass;
    private String email;
    private String type;

    public User() {
    }

    public User(int id, String name, String uname, String pass, String email, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.email = email;
        this.type = type;
    }

    public static User getFetch(String uname, String pass) {
        User user = null;
        String query = "SELECT * FROM users WHERE uname = ? AND pass = ?";
        try {
            PreparedStatement ps = DBConnector.getConnect().prepareStatement(query);
            ps.setString(1, uname);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                switch (rs.getString("type")) {
                    case "operator" -> user = new Operator();
                    case "agency" -> user = new Agency();
                    case "guest" -> user = new Guest();
                }
                assert user != null;
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUname(rs.getString("uname"));
                user.setPass(rs.getString("pass"));
                user.setEmail(rs.getString("email"));
                user.setType(rs.getString("type"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
