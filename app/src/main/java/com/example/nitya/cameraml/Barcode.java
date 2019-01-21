package com.example.nitya.cameraml;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.icu.util.ValueIterator;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.nitya.cameraml.Helper.GraphicOverlay;
import com.example.nitya.cameraml.Helper.TextGraphic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.ArrayList;
import java.util.List;

public class Barcode {

    public static void scanBarcodes(Bitmap bitmap, final GraphicOverlay graphicOverlay, final Context context, Button viewall) {
        // [START set_detector_options]
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionBarcodeDetectorOptions options =
                new FirebaseVisionBarcodeDetectorOptions.Builder()
                        .setBarcodeFormats(
                                FirebaseVisionBarcode.FORMAT_QR_CODE,
                                FirebaseVisionBarcode.FORMAT_AZTEC)
                        .build();
        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
                .getVisionBarcodeDetector();

        final ArrayList<String> arrBarcode=new ArrayList<>();

        Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> barcodes) {

                        Log.i("URL"," kaam to hua bhauya thoda");
                        for (FirebaseVisionBarcode barcode: barcodes) {
                            Rect bounds = barcode.getBoundingBox();
                            Point[] corners = barcode.getCornerPoints();

                            String rawValue = barcode.getRawValue();

                            int valueType = barcode.getValueType();

                            Log.e("Log", "QR Code: "+barcode.getDisplayValue()); //Returns barcode value in a user-friendly format.
                            Log.e("Log", "Raw Value: "+barcode.getRawValue());//Returns barcode value as it was encoded in the barcode.
                            Log.e("Log", "Code Type: "+barcode.getValueType());


                            // See API reference for complete list of supported types
                            switch (valueType) {
                                case FirebaseVisionBarcode.TYPE_WIFI:
                                    String ssid = barcode.getWifi().getSsid();
                                    String password = barcode.getWifi().getPassword();
                                    int type = barcode.getWifi().getEncryptionType();
                                    Log.i("URL"," is: ssid"+ssid+" pass"+password);
                                    arrBarcode.add("Ssid: "+ssid+" ,Password: "+password);
                                    break;
                                case FirebaseVisionBarcode.TYPE_URL:
                                    String title = barcode.getUrl().getTitle();
                                    String url = barcode.getUrl().getUrl();
                                    //TextGraphic textGraphic = new TextGraphic();
                                    //graphicOverlay.add(textGraphic);
                                    Log.i("URL"," is: title:"+title+" url:"+url);
                                    arrBarcode.add(url);
                                    break;
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Error",e.getMessage());
                    }
                });
        // [END run_detector]

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,ViewIt.class);
                intent.putStringArrayListExtra("words",arrBarcode);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }
}
