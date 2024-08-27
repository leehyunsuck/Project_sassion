package com.example.sassion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WriteActivity extends HomeActivity {

    static int diffCheck = -1;
    static int challSelect = -1;

    static int newImage = R.drawable.challenge_sticker_bed;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write); //챌린지 작성 화면


        CheckBox    c1 = findViewById(R.id.checkBox),
                    c2 = findViewById(R.id.checkBox2),
                    c3 = findViewById(R.id.checkBox3);

        ImageView   mainImg = findViewById(R.id.imageView7);

        EditText    title = findViewById(R.id.inputTitle),
                    content = findViewById(R.id.inputContent);
        
        Button  writeSuccessBtn = findViewById(R.id.chw1),
                moveButton = findViewById(R.id.back),
                image1 = findViewById(R.id.button);

        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editUserData = userData.edit();

        if (!userData.getString("challengeName", "").isEmpty()) {
            Intent moveHome = new Intent(getApplicationContext(), HomeActivity.class);
            Toast.makeText(this, "챌린지는 1개만 진행 가능합니다", Toast.LENGTH_SHORT).show();
            startActivity(moveHome);
        } else {
            challSelect = 1;
        }

        System.out.println("선택 : " + challSelect);

        //Button 누르면 화면전환
        //스티커 선택
        Intent intent1 = getIntent();
        if (intent1.hasExtra("change")){
            newImage = intent1.getIntExtra("change", R.drawable.challenge_sticker_bed);
            mainImg.setImageResource(newImage);
        }

        image1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v1) {
                Intent intent = new Intent(getApplicationContext(), ChoiceActivity.class);
                startActivity(intent);
            }

        });

        // 작성 완료 클릭시 액티 비티 전환
        writeSuccessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (diffCheck == -1 || title.getText().toString().isEmpty() || content.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "제목, 내용, 난이도를 전부 입력 , 선택 하셔야합니다", Toast.LENGTH_SHORT).show();
                } else {
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate = currentDate.format(formatter);

                    editUserData.putString("challengeName", title.getText().toString());
                    editUserData.putInt("challengeDiff", diffCheck);
                    editUserData.putString("challengeInfo", content.getText().toString());
                    editUserData.putInt("challengeStatus", 0);
                    editUserData.putBoolean("challengeSuccess", false);
                    editUserData.putString("challengeDate", formattedDate);
                    editUserData.putInt("challengeImg", newImage);
                    editUserData.commit();

                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    startActivity(intent);
                }
            }
        });

        // 뒤로가기 버튼 누르면 메인 화면으로 가기
        moveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v1) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        
        //체크박스
        CompoundButton.OnCheckedChangeListener checkClick = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = buttonView.getId();
                if (id == R.id.checkBox) {
                    if (isChecked) {
                        c2.setChecked(false);
                        c3.setChecked(false);
                        diffCheck = 1;
                    } else {
                        diffCheck = -1;
                    }
                } else if (id == R.id.checkBox2){
                    if (isChecked) {
                        c1.setChecked(false);
                        c3.setChecked(false);
                        diffCheck = 2;
                    } else {
                        diffCheck = -1;
                    }
                } else if ( id == R.id.checkBox3) {
                    if (isChecked) {
                        c1.setChecked(false);
                        c2.setChecked(false);
                        diffCheck = 3;
                    } else {
                        diffCheck = -1;
                    }
                }
            }
        };
        c1.setOnCheckedChangeListener(checkClick);
        c2.setOnCheckedChangeListener(checkClick);
        c3.setOnCheckedChangeListener(checkClick);
    }

}
