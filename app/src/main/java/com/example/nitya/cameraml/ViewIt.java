package com.example.nitya.cameraml;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewIt extends AppCompatActivity {

    ListView listView;
    ArrayList<String> finalWord=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_it);

        listView=findViewById(R.id.list);

        Intent intent=getIntent();

        finalWord=intent.getStringArrayListExtra("words");
        Log.i("msg", finalWord.get(0));
        contentview content=new contentview();
        listView.setAdapter(content);
    }

    class contentview extends BaseAdapter{

        @Override
        public int getCount() {
            return finalWord.size();
        }

        @Override
        public String getItem(int position) {
            return finalWord.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemview=getLayoutInflater().inflate(R.layout.activity_content_view,
                    parent,
                    false);
            final TextView contentview=itemview.findViewById(R.id.content);
            contentview.setText(getItem(position));

            ImageView copy=itemview.findViewById(R.id.copy);
            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("simple text",contentview.getText());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "String copied to Clipboard",
                            Toast.LENGTH_SHORT).show();
                }
            });
            return  itemview;
        }
    }

}
