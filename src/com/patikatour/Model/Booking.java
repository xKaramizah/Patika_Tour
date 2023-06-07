package com.patikatour.Model;

import java.util.Date;

public class Booking {
    private int id;
    private Date start_date;
    private Date end_date;
    private String guestName;
    private int roomID;

    public Booking(int id, Date start_date, Date end_date, String guestName, int roomID) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.guestName = guestName;
        this.roomID = roomID;
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

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
}
