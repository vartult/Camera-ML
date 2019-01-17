package com.example.nitya.cameraml;

import android.graphics.Bitmap;
import android.os.Parcelable;

import com.google.firebase.ml.vision.face.FirebaseVisionFace;

public class Face {

    String face;
    String smile;
    String accuracy;

    public Face(String face, String smile, String precision) {
        this.face=face;
        this.smile=smile;
        this.accuracy=precision;
    }

    public String getSmile() {
        return smile;
    }

    public String getFace() {
        return face;
    }

    public String getAccuracy() {
        return accuracy;
    }
}
