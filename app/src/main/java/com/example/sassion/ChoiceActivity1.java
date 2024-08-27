package com.example.sassion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class ChoiceActivity1 extends DetailActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice1);

        Intent intent1 = getIntent();

        SharedPreferences writeSP = getSharedPreferences("dayInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editSP = writeSP.edit();

        ImageButton imgbtn1 = findViewById(R.id.imageButton7);
        ImageButton imgbtn2 = findViewById(R.id.imageButton8);
        ImageButton imgbtn3 = findViewById(R.id.imageButton9);
        ImageButton imgbtn4 = findViewById(R.id.imageButton10);
        ImageButton imgbtn5 = findViewById(R.id.imageButton11);
        ImageButton imgbtn6 = findViewById(R.id.imageButton12);
        ImageButton imgbtn7 = findViewById(R.id.imageButton13);
        ImageButton imgbtn8 = findViewById(R.id.imageButton14);
        ImageButton imgbtn9 = findViewById(R.id.imageButton15);
        ImageButton imgbtn10 = findViewById(R.id.imageButton16);
        ImageButton imgbtn11 = findViewById(R.id.imageButton17);
        ImageButton imgbtn12 = findViewById(R.id.imageButton18);
        ImageButton imgbtn13 = findViewById(R.id.imageButton19);
        ImageButton imgbtn14 = findViewById(R.id.imageButton20);
        ImageButton imgbtn15 = findViewById(R.id.imageButton21);
        ImageButton imgbtn16 = findViewById(R.id.imageButton22);

        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editUserData = userData.edit();
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                Intent intent = new Intent(ChoiceActivity1.this, DetailActivity.class);

                int day = intent1.getIntExtra("day", 0);
                int tempId = -1;



                if (id == imgbtn1.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_01);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_01;
                } else if (id == imgbtn2.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_02);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_02;
                } else if (id == imgbtn3.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_03);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_03;
                } else if (id == imgbtn4.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_04);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_04;
                } else if (id == imgbtn5.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_05);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_05;
                } else if (id == imgbtn6.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_06);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_06;
                } else if (id == imgbtn7.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_07);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_07;
                } else if (id == imgbtn8.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_08);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_08;
                } else if (id == imgbtn9.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_09);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_09;
                }  else if (id == imgbtn10.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_10);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_10;
                } else if (id == imgbtn11.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_11);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_11;
                } else if (id == imgbtn12.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_12);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_12;
                } else if (id == imgbtn13.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_13);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_13;
                }  else if (id == imgbtn14.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_14);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_14;
                } else if (id == imgbtn15.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_15);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_15;
                } else if (id == imgbtn16.getId()) {
                    intent.putExtra("change", R.drawable.day_sticker_16);
                    intent.putExtra("day", day);
                    tempId = R.drawable.day_sticker_16;
                }

                int test = userData.getInt("DAY"+day, -1);
                if (test == -1 || test == 0) {
                    Toast.makeText(getApplicationContext(), "10포인트 지급!", Toast.LENGTH_SHORT).show();
                    int money = userData.getInt("money", 0) + 10;
                    editUserData.putInt("money", money);
                    editUserData.commit();
                }

                editUserData.putInt("DAY"+day, tempId);
                editUserData.commit();

                editSP.putInt(String.valueOf(day), tempId);
                editSP.commit();
                
                startActivity(intent);
            }
        };
        imgbtn1.setOnClickListener(click);
        imgbtn2.setOnClickListener(click);
        imgbtn3.setOnClickListener(click);
        imgbtn4.setOnClickListener(click);
        imgbtn5.setOnClickListener(click);
        imgbtn6.setOnClickListener(click);
        imgbtn7.setOnClickListener(click);
        imgbtn8.setOnClickListener(click);
        imgbtn9.setOnClickListener(click);
        imgbtn10.setOnClickListener(click);
        imgbtn11.setOnClickListener(click);
        imgbtn12.setOnClickListener(click);
        imgbtn13.setOnClickListener(click);
        imgbtn14.setOnClickListener(click);
        imgbtn15.setOnClickListener(click);
        imgbtn16.setOnClickListener(click);


    }
}


