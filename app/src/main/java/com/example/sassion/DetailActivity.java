package com.example.sassion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        Intent intent1 = getIntent();

        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        SharedPreferences writeSP = getSharedPreferences("dayInfo", Context.MODE_PRIVATE);

        // 테스트 코드
//        for (int day = 1; day <= 30; day++) {
//            int dayValue = userData.getInt("DAY" + day, 0);
//            System.out.println("DAY" + day + ": " + dayValue);
//        }


        TextView    viewTitle = findViewById(R.id.viewTitle),
                    viewContent = findViewById(R.id.viewContent);

        viewTitle.setText(userData.getString("challengeName", ""));
        viewContent.setText(userData.getString("challengeInfo", ""));

        // 뒤로가기 버튼 누르면 메인 화면으로 가기
        Button moveButton = findViewById(R.id.button31);
        moveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v1) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        //이미지 변환
        ImageView mainImg = findViewById(R.id.day_sticker_001);
        ImageView mainImg2 = findViewById(R.id.day_sticker_002);
        ImageView mainImg3 = findViewById(R.id.day_sticker_003);
        ImageView mainImg4 = findViewById(R.id.day_sticker_004);
        ImageView mainImg5 = findViewById(R.id.day_sticker_005);
        ImageView mainImg6 = findViewById(R.id.day_sticker_006);
        ImageView mainImg7 = findViewById(R.id.day_sticker_007);
        ImageView mainImg8 = findViewById(R.id.day_sticker_008);
        ImageView mainImg9 = findViewById(R.id.day_sticker_009);
        ImageView mainImg10 = findViewById(R.id.img0);
        ImageView mainImg11 = findViewById(R.id.img1);
        ImageView mainImg12 = findViewById(R.id.img2);
        ImageView mainImg13 = findViewById(R.id.img3);
        ImageView mainImg14 = findViewById(R.id.img4);
        ImageView mainImg15 = findViewById(R.id.img5);
        ImageView mainImg16 = findViewById(R.id.img6);
        ImageView mainImg17 = findViewById(R.id.img7);
        ImageView mainImg18 = findViewById(R.id.img8);
        ImageView mainImg19 = findViewById(R.id.img9);
        ImageView mainImg20 = findViewById(R.id.day_sticker_020);
        ImageView mainImg21 = findViewById(R.id.day_sticker_021);
        ImageView mainImg22 = findViewById(R.id.day_sticker_022);
        ImageView mainImg23 = findViewById(R.id.day_sticker_023);
        ImageView mainImg24 = findViewById(R.id.day_sticker_024);
        ImageView mainImg25 = findViewById(R.id.day_sticker_025);
        ImageView mainImg26 = findViewById(R.id.day_sticker_026);
        ImageView mainImg27 = findViewById(R.id.day_sticker_027);
        ImageView mainImg28 = findViewById(R.id.day_sticker_028);
        ImageView mainImg29 = findViewById(R.id.day_sticker_029);
        ImageView mainImg30 = findViewById(R.id.day_sticker_030);

        int clearDay = 0;
        for (int i = 1; i < 31; i++) {
            int newImg = userData.getInt("DAY"+i, -1);
            if (newImg != -1 && newImg != 0) {
                clearDay++;
                switch (i) {
                    case 1:
                        mainImg.setImageResource(newImg);
                        break;
                    case 2:
                        mainImg2.setImageResource(newImg);
                        break;
                    case 3:
                        mainImg3.setImageResource(newImg);
                        break;
                    case 4:
                        mainImg4.setImageResource(newImg);
                        break;
                    case 5:
                        mainImg5.setImageResource(newImg);
                        break;
                    case 6:
                        mainImg6.setImageResource(newImg);
                        break;
                    case 7:
                        mainImg7.setImageResource(newImg);
                        break;
                    case 8:
                        mainImg8.setImageResource(newImg);
                        break;
                    case 9:
                        mainImg9.setImageResource(newImg);
                        break;
                    case 10:
                        mainImg10.setImageResource(newImg);
                        break;
                    case 11:
                        mainImg11.setImageResource(newImg);
                        break;
                    case 12:
                        mainImg12.setImageResource(newImg);
                        break;
                    case 13:
                        mainImg13.setImageResource(newImg);
                        break;
                    case 14:
                        mainImg14.setImageResource(newImg);
                        break;
                    case 15:
                        mainImg15.setImageResource(newImg);
                        break;
                    case 16:
                        mainImg16.setImageResource(newImg);
                        break;
                    case 17:
                        mainImg17.setImageResource(newImg);
                        break;
                    case 18:
                        mainImg18.setImageResource(newImg);
                        break;
                    case 19:
                        mainImg19.setImageResource(newImg);
                        break;
                    case 20:
                        mainImg20.setImageResource(newImg);
                        break;
                    case 21:
                        mainImg21.setImageResource(newImg);
                        break;
                    case 22:
                        mainImg22.setImageResource(newImg);
                        break;
                    case 23:
                        mainImg23.setImageResource(newImg);
                        break;
                    case 24:
                        mainImg24.setImageResource(newImg);
                        break;
                    case 25:
                        mainImg25.setImageResource(newImg);
                        break;
                    case 26:
                        mainImg26.setImageResource(newImg);
                        break;
                    case 27:
                        mainImg27.setImageResource(newImg);
                        break;
                    case 28:
                        mainImg28.setImageResource(newImg);
                        break;
                    case 29:
                        mainImg29.setImageResource(newImg);
                        break;
                    case 30:
                        mainImg30.setImageResource(newImg);
                        break;
                    default:
                        break;
                }
            }
        }
        double clearDayDouble = (double) clearDay / 30 * 100;
        DecimalFormat df = new DecimalFormat("#.#");
        String clearDayDoubleFormat = df.format(clearDayDouble);

        TextView viewSuccess = findViewById(R.id.viewSuccess);
        viewSuccess.setText(clearDayDoubleFormat);

        if (intent1.hasExtra("change")){
            int newImg = intent1.getIntExtra("change", 0);
            int day = intent1.getIntExtra("day", 0);
            System.out.println(day);
            switch (day) {
                case 1:
                    mainImg.setImageResource(newImg);
                    break;
                case 2:
                    mainImg2.setImageResource(newImg);
                    break;
                case 3:
                    mainImg3.setImageResource(newImg);
                    break;
                case 4:
                    mainImg4.setImageResource(newImg);
                    break;
                case 5:
                    mainImg5.setImageResource(newImg);
                    break;
                case 6:
                    mainImg6.setImageResource(newImg);
                    break;
                case 7:
                    mainImg7.setImageResource(newImg);
                    break;
                case 8:
                    mainImg8.setImageResource(newImg);
                    break;
                case 9:
                    mainImg9.setImageResource(newImg);
                    break;
                case 10:
                    mainImg10.setImageResource(newImg);
                    break;
                case 11:
                    mainImg11.setImageResource(newImg);
                    break;
                case 12:
                    mainImg12.setImageResource(newImg);
                    break;
                case 13:
                    mainImg13.setImageResource(newImg);
                    break;
                case 14:
                    mainImg14.setImageResource(newImg);
                    break;
                case 15:
                    mainImg15.setImageResource(newImg);
                    break;
                case 16:
                    mainImg16.setImageResource(newImg);
                    break;
                case 17:
                    mainImg17.setImageResource(newImg);
                    break;
                case 18:
                    mainImg18.setImageResource(newImg);
                    break;
                case 19:
                    mainImg19.setImageResource(newImg);
                    break;
                case 20:
                    mainImg20.setImageResource(newImg);
                    break;
                case 21:
                    mainImg21.setImageResource(newImg);
                    break;
                case 22:
                    mainImg22.setImageResource(newImg);
                    break;
                case 23:
                    mainImg23.setImageResource(newImg);
                    break;
                case 24:
                    mainImg24.setImageResource(newImg);
                    break;
                case 25:
                    mainImg25.setImageResource(newImg);
                    break;
                case 26:
                    mainImg26.setImageResource(newImg);
                    break;
                case 27:
                    mainImg27.setImageResource(newImg);
                    break;
                case 28:
                    mainImg28.setImageResource(newImg);
                    break;
                case 29:
                    mainImg29.setImageResource(newImg);
                    break;
                case 30:
                    mainImg30.setImageResource(newImg);
                    break;
                default:
                    break;
            }
        }

        String startDate = userData.getString("challengeDate", "");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate now = LocalDate.now();

        Period period = Period.between(start, now.plusDays(1));
        int days = period.getDays();

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                Intent intent = new Intent(getApplicationContext(), ChoiceActivity1.class);

                LocalDate checkStart = start;
                LocalDate checkEnd = start;
                int day = 0;

                if (id == mainImg.getId()) {
                    checkStart = start.plusDays(0);
                    checkEnd = start.plusDays(1);
                    day = 1;
                } else if (id == mainImg2.getId()) {
                    checkStart = start.plusDays(1);
                    checkEnd = start.plusDays(2);
                    day = 2;
                } else if (id == mainImg3.getId()) {
                    checkStart = start.plusDays(2);
                    checkEnd = start.plusDays(3);
                    day = 3;
                } else if (id == mainImg4.getId()) {
                    checkStart = start.plusDays(3);
                    checkEnd = start.plusDays(4);
                    day = 4;
                } else if (id == mainImg5.getId()) {
                    checkStart = start.plusDays(4);
                    checkEnd = start.plusDays(5);
                    day = 5;
                } else if (id == mainImg6.getId()) {
                    checkStart = start.plusDays(5);
                    checkEnd = start.plusDays(6);
                    day = 6;
                } else if (id == mainImg7.getId()) {
                    checkStart = start.plusDays(6);
                    checkEnd = start.plusDays(7);
                    day = 7;
                } else if (id == mainImg8.getId()) {
                    checkStart = start.plusDays(7);
                    checkEnd = start.plusDays(8);
                    day = 8;
                } else if (id == mainImg9.getId()) {
                    checkStart = start.plusDays(8);
                    checkEnd = start.plusDays(9);
                    day = 9;
                } else if (id == mainImg10.getId()) {
                    checkStart = start.plusDays(9);
                    checkEnd = start.plusDays(10);
                    day = 10;
                } else if (id == mainImg11.getId()) {
                    checkStart = start.plusDays(10);
                    checkEnd = start.plusDays(11);
                    day = 11;
                } else if (id == mainImg12.getId()) {
                    checkStart = start.plusDays(11);
                    checkEnd = start.plusDays(12);
                    day = 12;
                } else if (id == mainImg13.getId()) {
                    checkStart = start.plusDays(12);
                    checkEnd = start.plusDays(13);
                    day = 13;
                } else if (id == mainImg14.getId()) {
                    checkStart = start.plusDays(13);
                    checkEnd = start.plusDays(14);
                    day = 14;
                } else if (id == mainImg15.getId()) {
                    checkStart = start.plusDays(14);
                    checkEnd = start.plusDays(15);
                    day = 15;
                } else if (id == mainImg16.getId()) {
                    checkStart = start.plusDays(15);
                    checkEnd = start.plusDays(16);
                    day = 16;
                } else if (id == mainImg17.getId()) {
                    checkStart = start.plusDays(16);
                    checkEnd = start.plusDays(17);
                    day = 17;
                } else if (id == mainImg18.getId()) {
                    checkStart = start.plusDays(17);
                    checkEnd = start.plusDays(18);
                    day = 18;
                } else if (id == mainImg19.getId()) {
                    checkStart = start.plusDays(18);
                    checkEnd = start.plusDays(19);
                    day = 19;
                } else if (id == mainImg20.getId()) {
                    checkStart = start.plusDays(19);
                    checkEnd = start.plusDays(20);
                    day = 20;
                } else if (id == mainImg21.getId()) {
                    checkStart = start.plusDays(20);
                    checkEnd = start.plusDays(21);
                    day = 21;
                } else if (id == mainImg22.getId()) {
                    checkStart = start.plusDays(21);
                    checkEnd = start.plusDays(22);
                    day = 22;
                } else if (id == mainImg23.getId()) {
                    checkStart = start.plusDays(22);
                    checkEnd = start.plusDays(23);
                    day = 23;
                } else if (id == mainImg24.getId()) {
                    checkStart = start.plusDays(23);
                    checkEnd = start.plusDays(24);
                    day = 24;
                } else if (id == mainImg25.getId()) {
                    checkStart = start.plusDays(24);
                    checkEnd = start.plusDays(25);
                    day = 25;
                } else if (id == mainImg26.getId()) {
                    checkStart = start.plusDays(25);
                    checkEnd = start.plusDays(26);
                    day = 26;
                } else if (id == mainImg27.getId()) {
                    checkStart = start.plusDays(26);
                    checkEnd = start.plusDays(27);
                    day = 27;
                } else if (id == mainImg28.getId()) {
                    checkStart = start.plusDays(27);
                    checkEnd = start.plusDays(28);
                    day = 28;
                } else if (id == mainImg29.getId()) {
                    checkStart = start.plusDays(28);
                    checkEnd = start.plusDays(29);
                    day = 29;
                } else if (id == mainImg30.getId()) {
                    checkStart = start.plusDays(29);
                    checkEnd = start.plusDays(30);
                    day = 30;
                }


                if ((now.isAfter(checkStart) || now.isEqual(checkStart)) && now.isBefore(checkEnd)) {
                    intent.putExtra("day", day);
                } else {
                    Toast.makeText(getApplicationContext(), "현재 가능한 일차 : "+days+"일차", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(intent);
            }
        };
        mainImg.setOnClickListener(click);
        mainImg2.setOnClickListener(click);
        mainImg3.setOnClickListener(click);
        mainImg4.setOnClickListener(click);
        mainImg5.setOnClickListener(click);
        mainImg6.setOnClickListener(click);
        mainImg7.setOnClickListener(click);
        mainImg8.setOnClickListener(click);
        mainImg9.setOnClickListener(click);
        mainImg10.setOnClickListener(click);
        mainImg11.setOnClickListener(click);
        mainImg12.setOnClickListener(click);
        mainImg13.setOnClickListener(click);
        mainImg14.setOnClickListener(click);
        mainImg15.setOnClickListener(click);
        mainImg16.setOnClickListener(click);
        mainImg17.setOnClickListener(click);
        mainImg18.setOnClickListener(click);
        mainImg19.setOnClickListener(click);
        mainImg20.setOnClickListener(click);
        mainImg21.setOnClickListener(click);
        mainImg22.setOnClickListener(click);
        mainImg23.setOnClickListener(click);
        mainImg24.setOnClickListener(click);
        mainImg25.setOnClickListener(click);
        mainImg26.setOnClickListener(click);
        mainImg27.setOnClickListener(click);
        mainImg28.setOnClickListener(click);
        mainImg29.setOnClickListener(click);
        mainImg30.setOnClickListener(click);
    }
}
