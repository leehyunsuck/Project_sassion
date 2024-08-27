package com.example.sassion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button  btnSetting = findViewById(R.id.buttonSetting),
                btnShop = findViewById(R.id.buttonShop),
                btnWrite = findViewById(R.id.buttonWrite),
                btnDetail = findViewById(R.id.detailMoveButton),
                btnHaru = findViewById(R.id.btnHaru);

        TextView viewMoney = findViewById(R.id.viewMoney),
                 viewTitle = findViewById(R.id.viewTitle),
                 viewContent = findViewById(R.id.viewContent),
                 showMsg = findViewById(R.id.showMsg);

        String[] msg = {
                "작은 습관이 큰 성과를 만들어냅니다!",
                "오늘의 노력은 내일의 자신을 만듭니다.",
                "목표를 향해 한 걸음씩 나아가 보세요!",
                "어려운 일도 한 단계씩 해결해 나갈 수 있어요.",
                "포기하지 않으면 언젠가는 이겨낼 수 있어요!",
                "당신은 더 강하고 더 용감할 수 있어요.",
                "죽음보다 무서운 것은 실패하지 않은 삶입니다.",
                "매일매일 나 자신에게 기대어보세요!",
                "어려움은 새로운 기회의 시작일 뿐입니다.",
                "노력하면 배울 수 있고, 배우면 성장할 수 있어요.",
                "오늘의 최선을 다하면 내일이 더 밝아질 거에요.",
                "성공의 비결은 꾸준한 노력과 포기하지 않는 의지입니다.",
                "자신을 믿어주세요. 당신은 할 수 있어요!",
                "어제보다 오늘이, 오늘보다 내일이 더 나은 하루가 될 거에요.",
                "마음을 열고 새로운 도전에 도전해 보세요!",
                "실패는 성공의 어머니입니다. 실패를 두려워하지 마세요.",
                "당신은 독특하고 특별한 존재에요. 그 사실을 기억하세요.",
                "고통을 이겨내면 더 강해질 수 있어요.",
                "꿈을 향해 달려가는 여정은 보상이 있을 거에요!",
                "포기하지 않고 끝까지 간다면 어떤 어려움도 이길 수 있어요!"
        };

        Random random = new Random();
        int randomIndex = random.nextInt(msg.length);

        showMsg.setText(msg[randomIndex]);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMsg.setText("");
            }
        }, 3000);

        Intent moveSetting = new Intent(this, OptionActivity.class);
        moveSetting.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent moveShop = new Intent(this, Shop.class);
        moveShop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent moveWrite = new Intent(this, WriteActivity.class);
        moveWrite.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent moveDetail = new Intent(this, DetailActivity.class);
        moveDetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        moveDetail.putExtra("selectDay", 1);

        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editUserData = userData.edit();

        viewMoney.setText(String.valueOf(userData.getInt("money", 0)));
        viewTitle.setText(userData.getString("challengeName", ""));
        viewContent.setText(userData.getString("challengeInfo", ""));

        ImageView   itemBanana = findViewById(R.id.itemBanana),
                    itemApple = findViewById(R.id.itemApple),
                    itemCup = findViewById(R.id.itemCup),
                    itemLight = findViewById(R.id.itemLight),
                    itemPhoto = findViewById(R.id.itemPhoto);

        itemLight.setVisibility(View.INVISIBLE);
        itemCup.setVisibility(View.INVISIBLE);
        itemPhoto.setVisibility(View.INVISIBLE);
        itemBanana.setVisibility(View.INVISIBLE);
        itemApple.setVisibility(View.INVISIBLE);

        if (userData.getBoolean("item0mounting", false)) itemLight.setVisibility(View.VISIBLE);
        if (userData.getBoolean("item1mounting", false)) itemCup.setVisibility(View.VISIBLE);
        if (userData.getBoolean("item2mounting", false)) itemPhoto.setVisibility(View.VISIBLE);
        if (userData.getBoolean("item3mounting", false)) itemBanana.setVisibility(View.VISIBLE);
        if (userData.getBoolean("item4mounting", false)) itemApple.setVisibility(View.VISIBLE);


        String startDate = userData.getString("challengeDate", "");

        if (!startDate.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate now = LocalDate.now();

            LocalDate checkEnd = start.plusDays(29);

            if (now.isAfter(checkEnd)) {
                //30일차
                Toast.makeText(getApplicationContext(), "챌린지 성공!", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "50포인트 지급!", Toast.LENGTH_SHORT).show();
                int money = userData.getInt("money", 0) + 50;
                editUserData.putInt("money", money);

                editUserData.putString("challengeName", "");
                editUserData.putInt("challengeDiff", 0);
                editUserData.putString("challengeInfo", "");
                editUserData.putInt("challengeStatus", 0);
                editUserData.putString("challengeDate", "");
                editUserData.putInt("challengeImg", -1);
                editUserData.putInt("DAY1", 0);
                editUserData.putInt("DAY2", 0);
                editUserData.putInt("DAY3", 0);
                editUserData.putInt("DAY4", 0);
                editUserData.putInt("DAY5", 0);
                editUserData.putInt("DAY6", 0);
                editUserData.putInt("DAY7", 0);
                editUserData.putInt("DAY8", 0);
                editUserData.putInt("DAY9", 0);
                editUserData.putInt("DAY10", 0);
                editUserData.putInt("DAY11", 0);
                editUserData.putInt("DAY12", 0);
                editUserData.putInt("DAY13", 0);
                editUserData.putInt("DAY14", 0);
                editUserData.putInt("DAY15", 0);
                editUserData.putInt("DAY16", 0);
                editUserData.putInt("DAY17", 0);
                editUserData.putInt("DAY18", 0);
                editUserData.putInt("DAY19", 0);
                editUserData.putInt("DAY20", 0);
                editUserData.putInt("DAY21", 0);
                editUserData.putInt("DAY22", 0);
                editUserData.putInt("DAY23", 0);
                editUserData.putInt("DAY24", 0);
                editUserData.putInt("DAY25", 0);
                editUserData.putInt("DAY26", 0);
                editUserData.putInt("DAY27", 0);
                editUserData.putInt("DAY28", 0);
                editUserData.putInt("DAY29", 0);
                editUserData.putInt("DAY30", 0);


                editUserData.commit();




                Intent movehome = new Intent(getApplicationContext(), HomeActivity.class);
                movehome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(movehome);
            } else {
                System.out.println("30일 안지남");
            }
        }

        



        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                if (id == btnSetting.getId()) {
                    startActivity(moveSetting);
                } else if (id == btnShop.getId()) {
                    startActivity(moveShop);
                } else if (id == btnWrite.getId()) {
                    startActivity(moveWrite);
                } else if (id == btnDetail.getId()) {
                    if (userData.getString("challengeName", "").isEmpty()) {
                        Toast.makeText(getApplicationContext(), "챌린지를 작성해주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(moveDetail);
                    }
                    
                } else if (id == btnHaru.getId()) {
                    Random random = new Random();
                    int randomIndex = random.nextInt(msg.length);

                    showMsg.setText(msg[randomIndex]);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showMsg.setText("");
                        }
                    }, 3000);
                }
            }
        };
        btnSetting.setOnClickListener(click);
        btnShop.setOnClickListener(click);
        btnWrite.setOnClickListener(click);
        btnDetail.setOnClickListener(click);
        btnHaru.setOnClickListener(click);
    }
}
