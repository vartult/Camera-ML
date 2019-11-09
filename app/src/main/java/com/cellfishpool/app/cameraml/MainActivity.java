package com.cellfishpool.app.cameraml;


import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btnText,btnImage,btnFaces,btnBarcode;
    int REQUEST_CODE=12;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnText=findViewById(R.id.btnText);
        btnBarcode=findViewById(R.id.btnBarcode);
        btnFaces=findViewById(R.id.btnFaces);
        //btnImage=findViewById(R.id.btnImages);


        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent select=new Intent(MainActivity.this,Select.class);
                select.putExtra("flag",1);
                startActivityForResult(select,REQUEST_CODE);

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

                Intent select=new Intent(MainActivity.this,Select.class);
                select.putExtra("flag",3);
                startActivityForResult(select,REQUEST_CODE);
            }
        });


        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent select=new Intent(MainActivity.this,Select.class);
                select.putExtra("flag",4);
                startActivityForResult(select,REQUEST_CODE);

            }
        });
    }

}
