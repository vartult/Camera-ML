
package com.example.nitya.cameraml;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.nitya.cameraml.Helper.TextGraphic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.Arrays;
import java.util.List;

public class TextRecognize {


    public static void recognizeText(Bitmap bitmap) {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionCloudTextRecognizerOptions options = new FirebaseVisionCloudTextRecognizerOptions.Builder()
                .setLanguageHints(Arrays.asList("en", "hi"))
                .build();
        // [END set_detector_options_cloud]

        // [START get_detector_cloud]
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getCloudTextRecognizer();
        // Or, to change the default settings:
        //   FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
        //          .getCloudTextRecognizer(options);
        // [END get_detector_cloud]

        // [START run_detector_cloud]
        Task<FirebaseVisionText> result = detector.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText result) {
                        // Task completed successfully
                        // [START_EXCLUDE]
                        // [START get_text_cloud]
                        for (FirebaseVisionText.TextBlock block : result.getTextBlocks()) {
                            Rect boundingBox = block.getBoundingBox();
                            Point[] cornerPoints = block.getCornerPoints();
                            String text = block.getText();

                            for (FirebaseVisionText.Line line : block.getLines()) {
                                // ...
                                for (FirebaseVisionText.Element element : line.getElements()) {
                                    // ...
                                }
                            }
                        }
                        // [END get_text_cloud]
                        // [END_EXCLUDE]
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // ...
                        Log.i("Error aa gyi",e.getMessage());
                    }
                });
    }


    /*private static void drawTextResult(FirebaseVisionText result) {

        Log.i("flaggggg", "yes here");
        String resultText = result.getText();
        for (FirebaseVisionText.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Log.i("helloworld", "kyabaat"+blockText);











            *//*List<FirebaseVisionText.TextBlock> blocks=firebaseVisionText.getTextBlocks();*//*

     *//*if(blocks.size()==0){
            //Toast.makeText(,"No TextFound!",Toast.LENGTH_SHORT).show();
            return;
        }

        graphicOverlay.clear();

        for(int i=0;i<blocks.size();i++) {

            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();

            for (int j = 0; j < lines.size(); j++) {

                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
for (int k = 0; k < elements.size(); k++) {

                    TextGraphic textGraphic = new TextGraphic(graphicOverlay, elements.get(k));
                    graphicOverlay.add(textGraphic);

                    Log.i("flaggggyyyyy",elements.get(k).toString());
                }
            }
        }*//*


        }
    }*/



}