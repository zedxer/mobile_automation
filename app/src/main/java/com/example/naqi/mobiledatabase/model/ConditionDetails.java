package com.example.naqi.mobiledatabase.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Naqi on 10/15/2017.
 */

public class ConditionDetails {
    private String name;
    private Drawable image;
    private boolean ischecked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public boolean ischecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public ConditionDetails(String name, Drawable image) {
        this.name = name;
        this.image = image;
    }
}
