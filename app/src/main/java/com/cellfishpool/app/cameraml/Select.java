package com.cellfishpool.app.cameraml;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cellfishpool.app.cameraml.Helper.GraphicOverlay;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Select extends AppCompatActivity {
    private static final int PERMISSION_REQUESTS = 1;
    int flag;
    private ImageView imageView;
    private Integer mImageMaxWidth;
    // Max height (portrait mode)
    private Integer mImageMaxHeight;
    private String imageFilePath = "";
    GraphicOverlay graphicOverlay;
    Button viewall,click;
    ArrayList<String> words;
    File photoFile=new File(String.valueOf(Uri.parse("android.resource://com.cellfishpool.app.cameraml/drawable/copy")));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        imageView = findViewById(R.id.image);
        graphicOverlay = findViewById(R.id.graphic);
        viewall = findViewById(R.id.viewall);
        click = findViewById(R.id.click);


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                words = new ArrayList<>();
                if(allPermissionsGranted()){
                    executeAfterPermission();

                }
                else {
                    getRuntimePermissions();
                    if(allPermissionsGranted())
                        executeAfterPermission();
                }
            }
});


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            //Bitmap picture = (Bitmap) data.getExtras().get("data");
           //picture.setPixel(5312,2988,1);
            //picture = BitmapFactory.decodeResource(getResources(),R.drawable.);
            //picture=decodeFile(imageFilePath);

            Bitmap picture = BitmapFactory.decodeFile(imageFilePath);

            Pair<Integer, Integer> targetedSize = getTargetedWidthHeight();

            int targetWidth = targetedSize.first;
            int maxHeight = targetedSize.second;


            // Determine how much to scale down the image

            try {
                float scaleFactor =
                        Math.max(
                                (float) picture.getWidth() / (float) targetWidth,
                                (float) picture.getHeight() / (float) maxHeight);


                picture =
                        Bitmap.createScaledBitmap(
                                picture,
                                (int) (picture.getWidth() / scaleFactor),
                                (int) (picture.getHeight() / scaleFactor),
                                false);
            }catch (NullPointerException e){
                /*Toast.makeText(getBaseContext(), "No data found please try again", Toast.LENGTH_LONG).show();
                Intent select = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(select);*/
            }
            /*Integer result=0;
            try {
                result=getRotationCompensation("my_camera_id",getParent(), getApplicationContext());
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            Matrix matrix = new Matrix();

            matrix.postRotate(result);

            picture = Bitmap.createBitmap(picture, 0, 0, picture.getWidth(), picture.getHeight(), matrix, true);*/

            imageView.setImageBitmap(picture);

            if (flag==1){
                //text recognition
                 TextRecognize obj=new TextRecognize();
                obj.recognizeText(graphicOverlay,picture,Select.this,viewall);
            }

            if (flag==2){
                //label images

                LabelingImages obj=new LabelingImages();
                obj.label(picture,getApplicationContext(),viewall);
            }

            if (flag==3){
                //recognize face

                FaceDetector obj=new FaceDetector();
                obj.detect(graphicOverlay,picture,getApplicationContext(),viewall);
            }

            if(flag==4){
                //scan barcode
                Barcode.scanBarcodes(picture,graphicOverlay,getApplicationContext(),viewall);
            }
        }
        else{
            //if image not found jump back to main home page with a toast
            Toast.makeText(getBaseContext(),"Error in Image Fetching",Toast.LENGTH_LONG).show();
            Intent select=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(select);
        }

    }

    public void executeAfterPermission(){
            Intent intent = getIntent();
            flag = intent.getIntExtra("flag", 0);



            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                Log.i("flagggggg", String.valueOf(flag));
                 //photoFile = null;
                try {
                    Log.i("new file","new file created");
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, 100);
            }

    }
    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false;
            }
        }
        return true;
    }
    private void getRuntimePermissions() {
        List allNeededPermissions = new ArrayList<>();
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                allNeededPermissions.add(permission);
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this, (String[]) allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
        }
    }
    private String[] getRequiredPermissions() {
        try {
            PackageInfo info =
                    this.getPackageManager()
                            .getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] ps = info.requestedPermissions;
            if (ps != null && ps.length > 0) {
                return ps;
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            return new String[0];
        }
    }

    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        if (allPermissionsGranted()) {
            Intent intent = getIntent();
            flag = intent.getIntExtra("flag", 0);
            /*Log.i("flagggggg111111111", String.valueOf(flag));
            Intent cameraIntent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
            startActivityForResult(cameraIntent, 100);*/
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private File createImageFile() throws IOException{

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }

    private Integer getImageMaxWidth() {
        if (mImageMaxWidth == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxWidth = imageView.getWidth();
        }

        return mImageMaxWidth;
    }

    // Returns max image height, always for portrait mode. Caller needs to swap width / height for
    // landscape mode.
    private Integer getImageMaxHeight() {
        if (mImageMaxHeight == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxHeight =
                    imageView.getHeight();
        }

        return mImageMaxHeight;
    }

    // Gets the targeted width / height.
    private Pair<Integer, Integer> getTargetedWidthHeight() {
        int targetWidth;
        int targetHeight;
        int maxWidthForPortraitMode = getImageMaxWidth();
        int maxHeightForPortraitMode = getImageMaxHeight();
        targetWidth = maxWidthForPortraitMode;
        targetHeight = maxHeightForPortraitMode;
        return new Pair<>(targetWidth, targetHeight);
    }





    //CAMERA ROTATION

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, Context context)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = getResources().getConfiguration().orientation;

        // On most devices, the sensor orientation is 90 degrees, but for some
        // devices it is 270 degrees. For devices with a sensor orientation of
        // 270, rotate the image an additional 180 ((270 + 270) % 360) degrees.
        CameraManager cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);
        rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360;

        // Return the corresponding FirebaseVisionImageMetadata rotation value.
        int result;
        switch (rotationCompensation) {
            case 0:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                break;
            case 90:
                result = FirebaseVisionImageMetadata.ROTATION_90;
                break;
            case 180:
                result = FirebaseVisionImageMetadata.ROTATION_180;
                break;
            case 270:
                result = FirebaseVisionImageMetadata.ROTATION_270;
                break;
            default:
                result = FirebaseVisionImageMetadata.ROTATION_0;
        }
        return result;
    }

}