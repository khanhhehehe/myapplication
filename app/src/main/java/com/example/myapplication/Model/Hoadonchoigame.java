package com.example.myapplication.Model;
public class Hoadonchoigame extends Hoadon {
    private String dateStart;
    private String dateEnd;
    private String Userid;
    private boolean isSuccess = false;
    private String Gameid;
    public Hoadonchoigame(float cost, String date, String userid) {
        super(cost);
        this.dateStart = date;
        Userid = userid;
    }
    public Hoadonchoigame() {
    }
    public String getDateEnd() {
        return dateEnd;
    }
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
    public String getDateStart() {
        return dateStart;
    }
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }
    public String getUserid() {
        return Userid;
    }
    public void setUserid(String userid) {
        Userid = userid;
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
        return "Hoadonchoigame{" +
                "cost=" + cost +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", Userid='" + Userid + '\'' +
                ", isSuccess=" + isSuccess +
                ", Gameid='" + Gameid + '\'' +
                '}';
    }
}