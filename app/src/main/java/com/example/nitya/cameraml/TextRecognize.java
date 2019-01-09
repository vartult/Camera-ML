
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



    public static void recognizeText(final GraphicOverlay graphicOverlay,Bitmap bitmap) {

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
                                if(firebaseVisionText!=null)
                                drawTextResult(graphicOverlay,firebaseVisionText);

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

    private static void drawTextResult(final GraphicOverlay graphicOverlay,FirebaseVisionText result) {

        Log.i("flaggggg", "yes here");
        String resultText = result.getText();
        for (FirebaseVisionText.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Log.i("helloworld", "kyabaat"+blockText);
            List<FirebaseVisionText.TextBlock> blocks=result.getTextBlocks();

        if(blocks.size()==0){
            //Toast.makeText(,"No TextFound!",Toast.LENGTH_SHORT).show();
            return;
        }

        graphicOverlay.clear();

        for(int i=0;i<blocks.size();i++) {

            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();

            for (int j = 0; j < lines.size(); j++) {

                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();

                for (int k = 0; k < elements.size(); k++) {
                    TextGraphic textGraphic = new TextGraphic(graphicOverlay, elements.get(j));
                    graphicOverlay.add(textGraphic);

                    Log.i("flaggggyyyyy",lines.get(j).toString());
                }
            }
        }


        }
    }



}
