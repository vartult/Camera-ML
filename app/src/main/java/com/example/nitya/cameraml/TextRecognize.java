
package com.example.nitya.cameraml;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

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

import dmax.dialog.SpotsDialog;

public class TextRecognize {


    public static void recognizeText(final GraphicOverlay graphicOverlay, Bitmap bitmap, final Context context, final Button viewall) {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        graphicOverlay.clear();

        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        // [END get_detector_default]

        // [START run_detector]

                detector.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                if(firebaseVisionText!=null)
                                drawTextResult(graphicOverlay,firebaseVisionText,context,viewall);
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

    private static void drawTextResult(final GraphicOverlay graphicOverlay, final FirebaseVisionText result, final Context context, Button viewall){

        final ArrayList<String> words=new ArrayList<>();

                for (FirebaseVisionText.TextBlock block : result.getTextBlocks()) {

                    String blockText = block.getText();
                    Log.i("Block Text", blockText);
                    //words.add(blockText);
                    List<FirebaseVisionText.Line> lines = block.getLines();


                    graphicOverlay.clear();

                    for (FirebaseVisionText.Line line : lines) {
                        String linetext = line.getText();
                        words.add(linetext);

                        GraphicOverlay.Graphic textGraphic = new TextGraphic(graphicOverlay, line);
                        graphicOverlay.add(textGraphic);
                        List<FirebaseVisionText.Element> elements = line.getElements();

                        for (FirebaseVisionText.Element element : elements) {

                            Log.i("element Text", element.getText());
                            //words.add(element.getText());

                            //GraphicOverlay.Graphic textGraphic = new TextGraphic(graphicOverlay, element);
                            //graphicOverlay.add(textGraphic);
                        }
                    }

                }


                //dialog.dismiss();






        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,ViewIt.class);
                intent.putStringArrayListExtra("words",words);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


        }


    }




