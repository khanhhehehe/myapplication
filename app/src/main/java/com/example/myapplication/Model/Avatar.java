package com.example.myapplication.Model;

import android.graphics.Bitmap;

public class Avatar {
   private String id;
   private Bitmap img;

    public Avatar(String id, Bitmap img) {
        this.id = id;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
