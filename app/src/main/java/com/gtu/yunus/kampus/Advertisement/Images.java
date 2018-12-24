package com.gtu.yunus.kampus.Advertisement;

import android.graphics.Bitmap;

public class Images {
    private String id;
    private Bitmap image;

    public Images(String id, Bitmap image) {
        this.id = id;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
