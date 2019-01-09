package com.example.nitya.cameraml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewIt extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_it);

        listView=findViewById(R.id.list);

        Intent intent=getIntent();
        ArrayList<String> word=new ArrayList<>();
        word=intent.getStringArrayListExtra("words");

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,word);
        listView.setAdapter(arrayAdapter);

    }
}
