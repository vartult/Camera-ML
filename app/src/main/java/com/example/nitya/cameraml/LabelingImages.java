package com.example.nitya.cameraml;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions;

import java.util.ArrayList;
import java.util.List;

public class LabelingImages {

    public void label(Bitmap bitmap, final Context context, Button viewall){

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionLabelDetectorOptions options=
                new FirebaseVisionLabelDetectorOptions.Builder()
                    .setConfidenceThreshold(0.8f)
                    .build();

        FirebaseVisionLabelDetector detector=FirebaseVision.getInstance()
                .getVisionLabelDetector(options);

        final ArrayList<String> arrLabel=new ArrayList<>();

        Task<List<FirebaseVisionLabel>> result=
                detector.detectInImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<List<FirebaseVisionLabel>>() {
                            @Override
                            public void onSuccess(List<FirebaseVisionLabel> firebaseVisionLabels) {

                                for (FirebaseVisionLabel label:firebaseVisionLabels){

                                    String text=label.getLabel();
                                    arrLabel.add(text);
                                    Log.i("qwert","yes");

                                    Log.i("Qwertyyyyy",text);
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.i("Errorrrrr",e.getMessage());

                    }
                });

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,ViewIt.class);
                intent.putStringArrayListExtra("words",arrLabel);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


    }

}
