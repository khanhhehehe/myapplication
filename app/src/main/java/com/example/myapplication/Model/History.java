package com.example.myapplication.Model;

public class History {
    private String userID;
    private String date;
    private String loaiGD;
    private float gia;

    public History() {
    }

    public History(String userID, String date, String loaiGD, float gia) {
        this.userID = userID;
        this.date = date;
        this.loaiGD = loaiGD;
        this.gia = gia;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLoaiGD() {
        return loaiGD;
    }

    public void setLoaiGD(String loaiGD) {
        this.loaiGD = loaiGD;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }
}
