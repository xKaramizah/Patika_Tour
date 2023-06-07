package com.patikatour.Model;

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

    public Room(int id, String type, byte bedNumber, boolean tv, boolean minibar, boolean gameConsole, boolean vault, boolean projection, double squareMeter, int hotelID) {
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
}
