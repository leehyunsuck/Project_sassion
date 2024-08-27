package com.example.sassion;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

public class ChoiceActivity extends WriteActivity{
//    int i = 0;

    public boolean onTouchEvent(MotionEvent e) {
        //화면 터치하면 이전화면으로 이동
        int action = e.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            //다음 화면이동 클래스 지정
            Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
            startActivity(intent);
        }
        return super.onTouchEvent(e);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sticker); //스티커 선택화면

        ImageButton imgbtn1 = findViewById(R.id.imageBu1);
        ImageButton imgbtn2 = findViewById(R.id.imageButton2);
        ImageButton imgbtn3 = findViewById(R.id.imageButton3);
        ImageButton imgbtn4 = findViewById(R.id.imageButton5);
        ImageButton imgbtn5 = findViewById(R.id.imageButton6);

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                Intent intent = new Intent(ChoiceActivity.this, WriteActivity.class);

                if (id == imgbtn1.getId()) {
                    intent.putExtra("change", R.drawable.challenge_write);
                } else if (id == imgbtn2.getId()) {
                    intent.putExtra("change", R.drawable.challenge_sticker_water);
                } else if (id == imgbtn3.getId()) {
                    intent.putExtra("change", R.drawable.challenge_sticker_notebook);
                } else if (id == imgbtn4.getId()) {
                    intent.putExtra("change", R.drawable.challenge_sticker_book);
                } else if (id == imgbtn5.getId()) {
                    intent.putExtra("change", R.drawable.challenge_sticker_etc);
                }

                startActivity(intent);
            }
        };
        imgbtn1.setOnClickListener(click);
        imgbtn2.setOnClickListener(click);
        imgbtn3.setOnClickListener(click);
        imgbtn4.setOnClickListener(click);
        imgbtn5.setOnClickListener(click);

    }
}
