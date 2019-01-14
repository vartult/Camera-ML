package com.example.nitya.cameraml;

import android.graphics.Bitmap;
import android.os.Parcelable;

public class Face {

    Bitmap face;
    String smile;
    String accuracy;

    public Face(Bitmap face, String smile, String precision) {
        this.face=face;
        this.smile=smile;
        this.accuracy=precision;
    }

    public String getSmile() {
        return smile;
    }

    public Bitmap getFace() {
        return face;
    }

    public String getAccuracy() {
        return accuracy;
    }
}
