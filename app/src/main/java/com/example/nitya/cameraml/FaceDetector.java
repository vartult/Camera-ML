package com.example.nitya.cameraml;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.nitya.cameraml.Helper.GraphicOverlay;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.L;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

import java.util.ArrayList;
import java.util.List;

public class FaceDetector {

    ArrayList<FirebaseVisionFace> arrayList=new ArrayList<>();

    public void detect(final GraphicOverlay graphicOverlay, Bitmap bitmap, final Button button) {


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
                                Log.i("Wohhoo","success");

                                processFaceList(graphicOverlay,firebaseVisionFaces,button);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Errorrrrrr caught", e.getMessage());

                            }
                        });

    }

    private void processFaceList(GraphicOverlay graphicOverlay,List<FirebaseVisionFace> faces,Button button) {

        for (FirebaseVisionFace face : faces) {
            arrayList.add(face);
            Log.i("wohhhooo2","enterd");
            Rect bounds = face.getBoundingBox();

            FaceGraphic faceGraphic = new FaceGraphic(graphicOverlay, face);
            graphicOverlay.add(faceGraphic);
            float rotY = face.getHeadEulerAngleY();  // Head is rotated to the right rotY degrees
            float rotZ = face.getHeadEulerAngleZ();  // Head is tilted sideways rotZ degrees

            // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
            // nose available):
            FirebaseVisionFaceLandmark leftEar = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR);
            if (leftEar != null) {
                FirebaseVisionPoint leftEarPos = leftEar.getPosition();
            }

            FirebaseVisionFaceLandmark rightEar = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR);
            if (rightEar != null) {
                FirebaseVisionPoint rightEarPos = leftEar.getPosition();
            }

            FirebaseVisionFaceLandmark leftCheek = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_CHEEK);
            if (leftCheek != null) {
                FirebaseVisionPoint leftCheekPos = leftEar.getPosition();
            }

            FirebaseVisionFaceLandmark rightCheek = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_CHEEK);
            if (rightCheek != null) {
                FirebaseVisionPoint rightCheekPos = leftEar.getPosition();
            }


            // If classification was enabled:
            if (face.getSmilingProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                float smileProb = face.getSmilingProbability();
            }
            if (face.getRightEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                float rightEyeOpenProb = face.getRightEyeOpenProbability();
            }

            // If face tracking was enabled:
            if (face.getTrackingId() != FirebaseVisionFace.INVALID_ID) {
                int id = face.getTrackingId();
            }

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent view = new Intent(FaceDetector.this,ViewIt.class);
                //view.putExtra("list",arrayList);

            }
        });


    }
}
