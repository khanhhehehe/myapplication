package com.example.myapplication.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Voucher implements Serializable {


    //loại 1 game nhà ma
    //loại 2 game nhà bóng
    //loại 3 game đua xe
    //loại 4 game bắn súng
    //loại 5 game nhún nhảy

    private String id;
    private int giamGia;
    private int loaiGame;
    private String maVoucher;
    private Object IDUser;
    private ArrayList ListUserId = new ArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Voucher() {
    }

    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

    public int getLoaiGame() {
        return loaiGame;
    }

    public void setLoaiGame(int loaiGame) {
        this.loaiGame = loaiGame;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public Object getIDUser() {
        return IDUser;
    }

    public void setIDUser(Object IDUser) {
        this.IDUser = IDUser;
    }

    public ArrayList getListUserId() {
        return ListUserId;
    }

    public void setListUserId(Object ob) {
        this.ListUserId = ((ArrayList) ob);
    }
}
