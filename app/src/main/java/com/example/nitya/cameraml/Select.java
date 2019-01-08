package com.example.nitya.cameraml;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nitya.cameraml.Helper.GraphicOverlay;
import com.example.nitya.cameraml.Helper.TextGraphic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.L;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.Arrays;
import java.util.List;

public class Select extends AppCompatActivity {

    int flag;
    CameraView cameraView;
    GraphicOverlay graphicOverlay;
    Button button;

    @Override
    protected void onResume() {
        super.onResume();

        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        cameraView.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        cameraView = findViewById(R.id.camera);
        graphicOverlay = findViewById(R.id.graphic);
        button = findViewById(R.id.viewall);

        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        Log.i("flagggggg111111111", String.valueOf(flag));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(Select.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Select.this, new String[]{
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                } else {
                    //you camera action as you want
                    // }
                    cameraView.start();
                    cameraView.captureImage();
                    graphicOverlay.clear();
                }


            }
        });

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, cameraView.getWidth(), cameraView.getHeight(), false);
                cameraView.stop();

                show(bitmap);

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

    }
    protected void show(Bitmap picture) {


        if (flag == 1) {
            //text recognition
            TextRecognize.recognizeText(graphicOverlay, picture);
        }

        if (flag == 2) {
            //label images

            LabelingImages obj = new LabelingImages();
            obj.label(picture);
        }

        if (flag == 3) {
            //recognize face

            FaceDetector obj = new FaceDetector();
            obj.detect(graphicOverlay, picture, button);
        }

        if (flag == 4) {
            //scan barcode
            Barcode.scanBarcodes(picture, graphicOverlay);
        } else {
            //if image not found jump back to main home page with a toast
            Toast.makeText(getBaseContext(), "Error in Image Fetching", Toast.LENGTH_LONG).show();
            Intent select = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(select);

        }
    }

}