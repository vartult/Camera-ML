package com.example.nitya.cameraml;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.nitya.cameraml.Helper.TextGraphic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.Arrays;
import java.util.List;

public class TextRecognize {


    private void recognizeText(Bitmap bitmap) {

        FirebaseVisionImage image=FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionCloudTextRecognizerOptions options=
                new FirebaseVisionCloudTextRecognizerOptions.Builder()
                        .setLanguageHints(Arrays.asList("en"))
                        .build();

        FirebaseVisionTextRecognizer textRecognizer=FirebaseVision.getInstance().getCloudTextRecognizer(options);

        textRecognizer.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {

                        if(firebaseVisionText!=null)
                            drawTextResult(firebaseVisionText);
                        //Toast.makeText(TextRecognize.this, "caught", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("Errorrrrrrrrrrr",e.getMessage());

            }
        });
    }

    private void drawTextResult(FirebaseVisionText firebaseVisionText) {

        Log.i("flaggggg","yes here");


        List<FirebaseVisionText.TextBlock> blocks=firebaseVisionText.getTextBlocks();

        if(blocks.size()==0){
            //Toast.makeText(,"No TextFound!",Toast.LENGTH_SHORT).show();
            return;
        }

        //graphicOverlay.clear();

        for(int i=0;i<blocks.size();i++) {

            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();

            for (int j = 0; j < lines.size(); j++) {

                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();

                for (int k = 0; k < elements.size(); k++) {

                    //TextGraphic textGraphic = new TextGraphic(graphicOverlay, elements.get(k));
                    //graphicOverlay.add(textGraphic);

                    Log.i("flaggggyyyyy",elements.get(k).toString());
                }
            }
        }


    }

}
