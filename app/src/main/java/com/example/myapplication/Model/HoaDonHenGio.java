package com.example.myapplication.Model;

public class HoaDonHenGio extends Hoadon {
    private String userId;
    private String timeStart;
    private String timeEnd;
    private boolean isSuccess = false;
    private boolean isCancel = false;
    private String Gameid;
    private String id;

    public HoaDonHenGio() {
    }

    public HoaDonHenGio(float cost, String userId, String timeStart, String timeEnd, String gameid) {
        super(cost);
        this.userId = userId;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.Gameid = gameid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getGameid() {
        return Gameid;
    }

    public void setGameid(String gameid) {
        Gameid = gameid;
    }

    @Override
    public String toString() {
        return "HoaDonHenGio{" +
                "userId='" + userId + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", isSuccess=" + isSuccess +
                ", isCancel=" + isCancel +
                ", Gameid='" + Gameid + '\'' +
                ", cost=" + cost +
                '}';
    }
}
