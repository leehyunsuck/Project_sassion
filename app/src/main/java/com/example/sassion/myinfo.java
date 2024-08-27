package com.example.sassion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class myinfo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        Button backButton = findViewById(R.id.back_bt);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivity(intent);
            }
        });

        Intent intent1 = getIntent();
        int selectDay = intent1.getIntExtra("selectDay", 0);
        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editUserData = userData.edit();
        String email = userData.getString("email", "");
        String name = userData.getString("name", "");

        TextView nTextView = findViewById(R.id.myname);
        nTextView.setText(name);
        TextView eTextView = findViewById(R.id.myemail);
        eTextView.setText(email);

        TextView    viewTitle1 = findViewById(R.id.chtit),
                viewContent1 = findViewById(R.id.chcnt);

        viewTitle1.setText(userData.getString("challengeName", ""));
        viewContent1.setText(userData.getString("challengeInfo", ""));

        TextView viewMoney = findViewById(R.id.viewMoney1);
        viewMoney.setText(String.valueOf(userData.getInt("money", 0)));

        TextView viewPoint = findViewById(R.id.viewpoint);

        int count = 0;
        for (int i = 1; i <= 30; i++) {
            if (userData.getInt("DAY"+i, 0) != 0) {
                count++;
            }
        }

        double clearDayDouble = (double) count / 30 * 100;
        DecimalFormat df = new DecimalFormat("#.#");
        String clearDayDoubleFormat = df.format(clearDayDouble);

        viewPoint.setText(clearDayDoubleFormat);

        Button chButton = findViewById(R.id.changeinfo_bt);
        chButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changePage = new Intent(getApplicationContext(), ChangeInfo.class);
                startActivity(changePage);
            }
        });



        Button clearAppDataButton = findViewById(R.id.logout_bt);
        clearAppDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userData.getString("email", "");
                String name = userData.getString("name", "");
                String imgUrl = userData.getString("imgUrl", "");
                String challengeName = userData.getString("challengeName", "");
                int challengeDiff = userData.getInt("challengeDiff", 0);
                String challengeInfo = userData.getString("challengeInfo", "");
                int challengeStatus = userData.getInt("challengeStatus", 0);
                boolean challengeSuccess = userData.getBoolean("challengeSuccess", false);
                String challengeDate = userData.getString("challengeDate", "");
                int challengeImg = userData.getInt("challengeImg", -1);
                int DAY1 = userData.getInt("DAY1", 0);
                int DAY2 = userData.getInt("DAY2", 0);
                int DAY3 = userData.getInt("DAY3", 0);
                int DAY4 = userData.getInt("DAY4", 0);
                int DAY5 = userData.getInt("DAY5", 0);
                int DAY6 = userData.getInt("DAY6", 0);
                int DAY7 = userData.getInt("DAY7", 0);
                int DAY8 = userData.getInt("DAY8", 0);
                int DAY9 = userData.getInt("DAY9", 0);
                int DAY10 = userData.getInt("DAY10", 0);
                int DAY11 = userData.getInt("DAY11", 0);
                int DAY12 = userData.getInt("DAY12", 0);
                int DAY13 = userData.getInt("DAY13", 0);
                int DAY14 = userData.getInt("DAY14", 0);
                int DAY15 = userData.getInt("DAY15", 0);
                int DAY16 = userData.getInt("DAY16", 0);
                int DAY17 = userData.getInt("DAY17", 0);
                int DAY18 = userData.getInt("DAY18", 0);
                int DAY19 = userData.getInt("DAY19", 0);
                int DAY20 = userData.getInt("DAY20", 0);
                int DAY21 = userData.getInt("DAY21", 0);
                int DAY22 = userData.getInt("DAY22", 0);
                int DAY23 = userData.getInt("DAY23", 0);
                int DAY24 = userData.getInt("DAY24", 0);
                int DAY25 = userData.getInt("DAY25", 0);
                int DAY26 = userData.getInt("DAY26", 0);
                int DAY27 = userData.getInt("DAY27", 0);
                int DAY28 = userData.getInt("DAY28", 0);
                int DAY29 = userData.getInt("DAY29", 0);
                int DAY30 = userData.getInt("DAY30", 0);
                int money = userData.getInt("money", 0);
                boolean item1 = userData.getBoolean("item1", false);
                boolean item2 = userData.getBoolean("item2", false);
                boolean item3 = userData.getBoolean("item3", false);
                boolean item4 = userData.getBoolean("item4", false);
                boolean item5 = userData.getBoolean("item5", false);
                boolean item6 = userData.getBoolean("item6", false);
                boolean item7 = userData.getBoolean("item7", false);
                boolean item8 = userData.getBoolean("item8", false);
                boolean item9 = userData.getBoolean("item9", false);
                boolean item0 = userData.getBoolean("item0", false);
                boolean item1mounting = userData.getBoolean("item1mounting", false);
                boolean item2mounting = userData.getBoolean("item2mounting", false);
                boolean item3mounting = userData.getBoolean("item3mounting", false);
                boolean item4mounting = userData.getBoolean("item4mounting", false);
                boolean item5mounting = userData.getBoolean("item5mounting", false);
                boolean item6mounting = userData.getBoolean("item6mounting", false);
                boolean item7mounting = userData.getBoolean("item7mounting", false);
                boolean item8mounting = userData.getBoolean("item8mounting", false);
                boolean item9mounting = userData.getBoolean("item9mounting", false);
                boolean item0mounting = userData.getBoolean("item0mounting", false);


                String url = "http://sassion.kro.kr:8080/sassion/updateinfo.jsp";

                String parameters = "";
                try {
                    parameters = "email=" + URLEncoder.encode(email, "UTF-8") +
                            "&name=" + URLEncoder.encode(name, "UTF-8") +
                            "&imgUrl=" + URLEncoder.encode(imgUrl, "UTF-8") +
                            "&challengeName=" + URLEncoder.encode(challengeName, "UTF-8") +
                            "&challengeDiff=" + challengeDiff +
                            "&challengeInfo=" + URLEncoder.encode(challengeInfo, "UTF-8") +
                            "&challengeStatus=" + challengeStatus +
                            "&challengeSuccess=" + challengeSuccess +
                            "&challengeDate=" + URLEncoder.encode(challengeDate, "UTF-8") +
                            "&challengeImg=" + challengeImg +
                            "&DAY1=" + DAY1 +
                            "&DAY2=" + DAY2 +
                            "&DAY3=" + DAY3 +
                            "&DAY4=" + DAY4 +
                            "&DAY5=" + DAY5 +
                            "&DAY6=" + DAY6 +
                            "&DAY7=" + DAY7 +
                            "&DAY8=" + DAY8 +
                            "&DAY9=" + DAY9 +
                            "&DAY10=" + DAY10 +
                            "&DAY11=" + DAY11 +
                            "&DAY12=" + DAY12 +
                            "&DAY13=" + DAY13 +
                            "&DAY14=" + DAY14 +
                            "&DAY15=" + DAY15 +
                            "&DAY16=" + DAY16 +
                            "&DAY17=" + DAY17 +
                            "&DAY18=" + DAY18 +
                            "&DAY19=" + DAY19 +
                            "&DAY20=" + DAY20 +
                            "&DAY21=" + DAY21 +
                            "&DAY22=" + DAY22 +
                            "&DAY23=" + DAY23 +
                            "&DAY24=" + DAY24 +
                            "&DAY25=" + DAY25 +
                            "&DAY26=" + DAY26 +
                            "&DAY27=" + DAY27 +
                            "&DAY28=" + DAY28 +
                            "&DAY29=" + DAY29 +
                            "&DAY30=" + DAY30 +
                            "&money=" + money +
                            "&item1=" + item1 +
                            "&item2=" + item2 +
                            "&item3=" + item3 +
                            "&item4=" + item4 +
                            "&item5=" + item5 +
                            "&item6=" + item6 +
                            "&item7=" + item7 +
                            "&item8=" + item8 +
                            "&item9=" + item9 +
                            "&item0=" + item0 +
                            "&item1mounting=" + item1mounting +
                            "&item2mounting=" + item2mounting +
                            "&item3mounting=" + item3mounting +
                            "&item4mounting=" + item4mounting +
                            "&item5mounting=" + item5mounting +
                            "&item6mounting=" + item6mounting +
                            "&item7mounting=" + item7mounting +
                            "&item8mounting=" + item8mounting +
                            "&item9mounting=" + item9mounting +
                            "&item0mounting=" + item0mounting;
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                OkHttpClient client = new OkHttpClient();


                Request request = new Request.Builder()
                        .url(url + "?" + parameters)
                        .get()
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            System.out.println("응답 본문: " + myResponse);
                            try {
                                SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editUserData = userData.edit();
                                editUserData.clear();
                                editUserData.commit();

                                SharedPreferences writeSP = getSharedPreferences("dayInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editSP = writeSP.edit();
                                editSP.clear();
                                editSP.commit();
                                Intent moveLogin = new Intent(getApplicationContext(), Login.class);
                                startActivity(moveLogin);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            System.out.println("Error: " + response.code());
                        }
                    }
                });

            }
        });
    }
}