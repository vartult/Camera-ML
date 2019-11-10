package com.cellfishpool.app.cameraml;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Integer mImageMaxWidth;
    // Max height (portrait mode)
    private Integer mImageMaxHeight;
    File photoFile = new File(String.valueOf(Uri.parse("android.resource://com.cellfishpool.app.cameraml/drawable/copy")));
    private static final int PERMISSION_REQUESTS = 1;
    int flag;
    Button btnText, btnImage, btnFaces, btnBarcode;
    int REQUEST_CODE = 12;
    ImageView imageView;
    private String imageFilePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnText = findViewById(R.id.btnText);
        btnBarcode = findViewById(R.id.btnBarcode);
        btnFaces = findViewById(R.id.btnFaces);

        if (!allPermissionsGranted())
            getRuntimePermissions();
        //btnImage=findViewById(R.id.btnImages);


        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencamera();

                Intent select = new Intent(MainActivity.this, Select.class);
                select.putExtra("link", imageFilePath);
                select.putExtra("flag", 1);
                startActivityForResult(select, REQUEST_CODE);


            }
        });


//        btnImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent select=new Intent(MainActivity.this,Select.class);
//                select.putExtra("flag",2);
//                startActivityForResult(select,REQUEST_CODE);
//
//            }
//        });


        btnFaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencamera();


                Intent select = new Intent(MainActivity.this, Select.class);
                select.putExtra("link", imageFilePath);
                select.putExtra("flag", 3);
                startActivityForResult(select, REQUEST_CODE);

            }
        });


        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencamera();
                Intent select = new Intent(MainActivity.this, Select.class);
                select.putExtra("link", imageFilePath);
                select.putExtra("flag", 4);
                startActivityForResult(select, REQUEST_CODE);

            }
        });
    }

    private void opencamera() {
        if (allPermissionsGranted()) {
            executeAfterPermission();

        } else {
            getRuntimePermissions();
            if (allPermissionsGranted())
                executeAfterPermission();
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

    public void executeAfterPermission() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Log.i("flagggggg", String.valueOf(flag));
            //photoFile = null;
            try {
                Log.i("new file", "new file created");
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

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
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
            } catch (NullPointerException e) {
                /*Toast.makeText(getBaseContext(), "No data found please try again", Toast.LENGTH_LONG).show();
                Intent select = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(select);*/
            }
        }
    }

    private Integer getImageMaxWidth () {
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
    private Integer getImageMaxHeight () {
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
    private Pair<Integer, Integer> getTargetedWidthHeight(){
        int targetWidth;
        int targetHeight;
        int maxWidthForPortraitMode = getImageMaxWidth();
        int maxHeightForPortraitMode = getImageMaxHeight();
        targetWidth = maxWidthForPortraitMode;
        targetHeight = maxHeightForPortraitMode;
        return new Pair<>(targetWidth, targetHeight);
    }
}