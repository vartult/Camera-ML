package com.example.nitya.cameraml;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.nitya.cameraml.Helper.FaceGraphic;
import com.example.nitya.cameraml.Helper.GraphicOverlay;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FaceDetector {

    ArrayList<String> fArray = new ArrayList<>();
    ArrayList<String> smileArray = new ArrayList<>();
    ArrayList<String> precArray = new ArrayList<>();

    public void detect(final GraphicOverlay graphicOverlay, final Bitmap bitmap, final Context context, final Button button) {
        graphicOverlay.clear();

        FirebaseVisionFaceDetectorOptions highAccuracy =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .enableTracking()
                        .build();

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance().getVisionFaceDetector(highAccuracy);

        Task<List<FirebaseVisionFace>> result =
                detector.detectInImage(image)
                        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                            @Override
                            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                                Log.i("Wohhoo", "success");

                                processFaceList(graphicOverlay, firebaseVisionFaces, context, button);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Errorrrrrr caught", e.getMessage());

                            }
                        });

    }

    private void processFaceList(GraphicOverlay graphicOverlay, List<FirebaseVisionFace> faces, final Context context, Button button) {
        int i=0;

        graphicOverlay.clear();

        for (FirebaseVisionFace face : faces) {
            i++;

            Log.i("wohhhooo2", "enterd");

            FaceGraphic faceGraphic = new FaceGraphic(graphicOverlay, face,String.valueOf(i));
            graphicOverlay.add(faceGraphic);

            double smileProb = 0.0f;
            double notSmileProb=0.0f;

            // If classification was enabled:
            if (face.getSmilingProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                smileProb = face.getSmilingProbability() * 100;
            }
            smileProb = Math.round(smileProb*1000.0)/1000.0;
            notSmileProb=Math.round((100-smileProb)*1000.0)/1000.0;
            String smile, precision;
            if (smileProb > 50.0) {
                smile = "Smiling";
                precision = "With precision of " + smileProb + " %";
            } else {
                smile = "Not Smiling";
                precision = "With precision of " + notSmileProb + " %";

            }

            fArray.add(String.valueOf(i));
            smileArray.add(smile);
            precArray.add(precision);

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent view = new Intent(context, ViewFaces.class);
                view.putStringArrayListExtra("face",fArray);
                view.putStringArrayListExtra("smile",smileArray);
                view.putStringArrayListExtra("precision",precArray);
                context.startActivity(view);

            }
        });
    }
}
