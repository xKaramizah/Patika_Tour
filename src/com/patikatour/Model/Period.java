package com.patikatour.Model;

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
