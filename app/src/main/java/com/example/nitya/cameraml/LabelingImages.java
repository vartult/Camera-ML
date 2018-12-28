package com.example.nitya.cameraml;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions;

import java.util.List;

public class LabelingImages {

    public void label(Bitmap bitmap){

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionLabelDetectorOptions options=
                new FirebaseVisionLabelDetectorOptions.Builder()
                    .setConfidenceThreshold(0.8f)
                    .build();

        FirebaseVisionLabelDetector detector=FirebaseVision.getInstance()
                .getVisionLabelDetector(options);

        Task<List<FirebaseVisionLabel>> result=
                detector.detectInImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<List<FirebaseVisionLabel>>() {
                            @Override
                            public void onSuccess(List<FirebaseVisionLabel> firebaseVisionLabels) {

                                for (FirebaseVisionLabel label:firebaseVisionLabels){

                                    String text=label.getLabel();
                                    String entityId=label.getEntityId();
                                    float confidence=label.getConfidence();

                                    Log.i("Qwertyyyyy",text+"  "+entityId+"  "+confidence);
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.i("Errorrrrr",e.getMessage());

                    }
                });


    }

}
