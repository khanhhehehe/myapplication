package com.example.myapplication.Model;

public class PlayTime {
    private int id;
    private int img;

    public PlayTime() {
    }

    public PlayTime(int id, int img) {
        this.id = id;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
