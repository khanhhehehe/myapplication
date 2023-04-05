package com.example.myapplication.Model;

import java.io.Serializable;

public class Game implements Serializable {

    //loại 1 game nhà ma
    //loại 2 game nhà bóng
    //loại 3 game đua xe
    //loại 4 game bắn súng
    //loại 5 game nhún nhảy


    private int id;
    private int gia;
    private String kieu;
    private String tenGame;
    private String trangThai;
    private String moTa;
    private boolean isPlaying;
    private int imgGame;



    public Game() {
    }

    public Game(int id, int gia, String kieu, String tenGame, String trangThai) {
        this.id = id;
        this.gia = gia;
        this.kieu = kieu;
        this.tenGame = tenGame;
        this.trangThai = trangThai;


    }

    public boolean isPlaying() {
        return isPlaying;
    }



    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getKieu() {
        return kieu;
    }

    public void setKieu(String kieu) {
        this.kieu = kieu;
    }

    public String getTenGame() {
        return tenGame;
    }

    public void setTenGame(String tenGame) {
        this.tenGame = tenGame;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getImgGame() {
        return imgGame;
    }

    public void setImgGame(int imgGame) {
        this.imgGame = imgGame;
    }
}
