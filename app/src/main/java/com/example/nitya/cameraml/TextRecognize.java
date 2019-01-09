
package com.example.nitya.cameraml;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nitya.cameraml.Helper.GraphicOverlay;
import com.example.nitya.cameraml.Helper.TextGraphic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.L;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;
import java.util.List;

public class TextRecognize {
    static ArrayList<String> qwer;


    public static void recognizeText(final GraphicOverlay graphicOverlay, Bitmap bitmap) {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        // [END get_detector_default]

        // [START run_detector]

        final ArrayList<String>[] words=new ArrayList[1];
                detector.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                Log.i("ho gya ye","qwertyuiop");
                                if(firebaseVisionText!=null)
                                words[0] =drawTextResult(graphicOverlay,firebaseVisionText);
                                Log.i("some3",words[0].toString());

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
                qwer=words[0];
    }

    private static ArrayList<String> drawTextResult(final GraphicOverlay graphicOverlay,FirebaseVisionText result) {

        ArrayList<String> words=new ArrayList<>();

        Log.i("flaggggg", "yes here");
        String resultText = result.getText();

        for (FirebaseVisionText.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Log.i("helloworld", "kyabaat"+blockText);
            List<FirebaseVisionText.Line> lines=block.getLines();

        graphicOverlay.clear();

        for(FirebaseVisionText.Line line:lines ) {

                List<FirebaseVisionText.Element> elements = line.getElements();

                for (FirebaseVisionText.Element element:elements){

                    Log.i("qwertyyyy",element.getText());
                    words.add(element.getText());

                    TextGraphic textGraphic = new TextGraphic(graphicOverlay, element);
                    graphicOverlay.add(textGraphic);
                }
            }
        }

        return words;


        }
    public static ArrayList<String> getList(){
        return qwer;
    }

    }




