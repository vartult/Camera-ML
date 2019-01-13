package com.example.nitya.cameraml;

import android.graphics.Bitmap;

import com.google.firebase.ml.vision.face.FirebaseVisionFace;

public class Face {

    FirebaseVisionFace face;
    String smile;
    String accuracy;

    public Face(FirebaseVisionFace face, String smile, String precision) {
        this.face=face;
        this.smile=smile;
        this.accuracy=precision;
    }

    public String getSmile() {
        return smile;
    }

    public FirebaseVisionFace getFace() {
        return face;
    }

    public String getAccuracy() {
        return accuracy;
    }
}
