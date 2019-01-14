package com.example.nitya.cameraml;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class contentView extends AppCompatActivity {
    ImageView copy;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view);
        text=findViewById(R.id.content);
        copy=findViewById(R.id.copy);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text",text.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "String copied to Clipboard",
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}
