package com.example.nitya.cameraml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewFaces extends AppCompatActivity {

    ListView listView;
    ArrayList<? extends Face> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faces);

        listView=findViewById(R.id.faceList);

        //Intent intent=getIntent();
        //arr= (ArrayList<Face>) intent.getParcelableExtra("list");

        Bundle bundle=this.getIntent().getExtras();
        arr=savedInstanceState.getParcelableArrayList("list");
        CustomAdapter customAdapter=new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Face getItem(int i) {
            return arr.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View inflater=getLayoutInflater().inflate(
                    R.layout.face_element,
                    viewGroup,
                    false
            );

            TextView smile=inflater.findViewById(R.id.textFace);
            TextView precision=inflater.findViewById(R.id.textPrecision);
            ImageView image=inflater.findViewById(R.id.imageFace);

            smile.setText(getItem(i).getSmile());
            precision.setText(getItem(i).getAccuracy());
            //image.setImageBitmap(getItem(i).getFace());

            return inflater;
        }
    }
}
