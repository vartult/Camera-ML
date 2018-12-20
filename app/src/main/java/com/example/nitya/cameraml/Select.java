package com.example.nitya.cameraml;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class Select extends AppCompatActivity {

    int flag;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        imageView=findViewById(R.id.show);

        Intent intent=getIntent();
        flag=intent.getIntExtra("flag",0);
        Log.i("flagggggg111111111",String.valueOf(flag));

        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,100);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 100 && resultCode == RESULT_OK) {

                MediaStore.Images.Media picture1 = (MediaStore.Images.Media) data.getExtras().get("data");
                Bitmap picture = (Bitmap) data.getExtras().get("data"); //this is your bitmap image and now you can do whatever you want with this
                imageView.setImageBitmap(picture); //for example I put bmp in an ImageView

                Log.i("flaggggggggg1111111", String.valueOf(flag));
            }
        }
    }
}
