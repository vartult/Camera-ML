package com.example.nitya.cameraml;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.nitya.cameraml.Helper.GraphicOverlay;
import com.example.nitya.cameraml.Helper.TextGraphic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.util.Arrays;
import java.util.List;

public class TextRecognize {
    private static GraphicOverlay graphicOverlay;



    public static void recognizeText(Bitmap bitmap) {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        // [END get_detector_default]

        // [START run_detector]
                detector.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                Log.i("ho gya ye","qwertyuiop");
                                for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                                    String blockText = block.getText();
                                    Float blockConfidence = block.getConfidence();
                                    List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
                                    Point[] blockCornerPoints = block.getCornerPoints();
                                    Rect blockFrame = block.getBoundingBox();

                                    Log.i("ho gya ye","msg="+blockText);
                                    for (FirebaseVisionText.Line line: block.getLines()) {
                                        // ...
                                        for (FirebaseVisionText.Element element: line.getElements()) {

                                        }
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("Error","Error in ML");
                                        // Task failed with an exception
                                        // ...
                                    }
                                });
    }



        /*private static void drawTextResult(FirebaseVisionText firebaseVisionText) {

            Log.i("flaggggg", "yes here");


            List<FirebaseVisionText.TextBlock> blocks = firebaseVisionText.getTextBlocks();

            if (blocks.size() == 0) {
                Log.i("Error", "Img error");
                //Toast.makeText(,"No TextFound!",Toast.LENGTH_SHORT).show();
                return;
            }

            graphicOverlay.clear();

            for (int i = 0; i < blocks.size(); i++) {

                List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();

                for (int j = 0; j < lines.size(); j++) {

                    List<FirebaseVisionText.Element> elements = lines.get(j).getElements();

                    for (int k = 0; k < elements.size(); k++) {

                        TextGraphic textGraphic = new TextGraphic(graphicOverlay, elements.get(k));
                        graphicOverlay.add(textGraphic);

                        Log.i("flaggggyyyyy", elements.get(k).toString());
                    }
                }
            }


        }*/




}
