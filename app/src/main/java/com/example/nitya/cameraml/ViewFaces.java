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

import com.google.firebase.ml.vision.face.FirebaseVisionFace;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewFaces extends AppCompatActivity {

    ListView listView;
    ArrayList<Face> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faces);

        arr=new ArrayList<>();

        listView=findViewById(R.id.faceList);

        Intent intent=getIntent();
        ArrayList<String> face= intent.getStringArrayListExtra("face");
        ArrayList<String> smile=intent.getStringArrayListExtra("smile");
        ArrayList<String> precision=intent.getStringArrayListExtra("precision");

        for (int i=0;i<face.size();i++)
            arr.add(new Face(face.get(i),smile.get(i),precision.get(i)));

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
            TextView image=inflater.findViewById(R.id.imageFace);

            smile.setText(getItem(i).getSmile());
            precision.setText(getItem(i).getAccuracy());
            image.setText(getItem(i).getFace());

            return inflater;
        }
    }
}
