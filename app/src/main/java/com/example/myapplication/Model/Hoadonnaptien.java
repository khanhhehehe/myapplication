package com.example.myapplication.Model;
public class Hoadonnaptien extends Hoadon {
    private String userId;
    //thời gian hiện tại
    private String date;
    //thời gian được lấy để biết khi nào máy tắt
    private long durationtime;
    //trạng thái xem hoá đơn đã được xử lý chưa
    private boolean trangThai = false;
    public Hoadonnaptien(float cost, String userId, String date, boolean trangThai) {
        super(cost);
        this.userId = userId;
        this.date = date;
        this.trangThai = trangThai;
    }
    public Hoadonnaptien() {
    }
    public boolean isTrangThai() {
        return trangThai;
    }
    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Hoadonnaptien{" +
                "cost=" + cost +
                ", userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                ", durationtime=" + durationtime +
                ", trangThai=" + trangThai +
                '}';
    }
}