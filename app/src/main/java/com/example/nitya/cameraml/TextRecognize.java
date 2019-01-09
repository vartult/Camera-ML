
package com.example.nitya.cameraml;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class TextRecognize {

    public static void recognizeText(final GraphicOverlay graphicOverlay, Bitmap bitmap) {

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
            List<FirebaseVisionText.Line> lines=block.getLines();

        if(result==null){
            //Toast.makeText(,"No TextFound!",Toast.LENGTH_SHORT).show();
            return;
        }

        graphicOverlay.clear();

        for(FirebaseVisionText.Line line:lines ) {

                List<FirebaseVisionText.Element> elements = line.getElements();

                for (FirebaseVisionText.Element element:elements){

                    Log.i("qwertyyyy",element.getText());

                    TextGraphic textGraphic = new TextGraphic(graphicOverlay, element);
                    graphicOverlay.add(textGraphic);
                }
            }
        }


        }
    }




