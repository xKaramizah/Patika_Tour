package com.patikatour.Model;

public class Price {
    private int id;
    private double price;
    private int roomID;
    private int periodID;

    public Price(int id, double price, int roomID, int periodID) {
        this.id = id;
        this.price = price;
        this.roomID = roomID;
        this.periodID = periodID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getPeriodID() {
        return periodID;
    }

    public void setPeriodID(int periodID) {
        this.periodID = periodID;
    }
}
