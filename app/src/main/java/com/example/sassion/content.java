package com.example.sassion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class content extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Button backButton = findViewById(R.id.back_bt);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notice.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            String tit = intent.getStringExtra("tit");
            String content = intent.getStringExtra("content");

            TextView titTextView = findViewById(R.id.titTextView);
            TextView contentTextView = findViewById(R.id.contentTextView);

            titTextView.setText(tit);
            contentTextView.setText(content);
        }
    }
}
