package com.example.myapplication.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class User implements Serializable, Cloneable {
    private String id;
    private String name;
    private String password;
    private String phonenumber;
    private long sodu;
    private Bitmap avatar;


    public User() {
    }

    public User(String id, String name, String password, String phonenumber, long sodu) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phonenumber = phonenumber;
        this.sodu = sodu;
    }

    public User(String name, String password, String phonenumber, long sodu) {
        this.name = name;
        this.password = password;
        this.phonenumber = phonenumber;
        this.sodu = sodu;
    }

    public User Clone() {
        User u = null;
        try {
            u = (User) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return u;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public long getSodu() {
        return sodu;
    }

    public void setSodu(long sodu) {
        this.sodu = sodu;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}

