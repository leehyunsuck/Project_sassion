package com.example.sassion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //이동 페이지(Intent) 객체
        Intent moveRegister = new Intent(this, Register.class);
        Intent moveFindPassword = new Intent(this, FindPassword.class);
        Intent moveFirst = new Intent(this, MainActivity.class);

        //버튼 객체
        Button login = findViewById(R.id.loginButton);
        Button findPassword = findViewById(R.id.findPasswordButton);
        Button register = findViewById(R.id.registerButton);

        //입력 객체
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);

        TextView warningView = findViewById(R.id.warningMsg);

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                if (id == register.getId()) {
                    startActivity(moveRegister);
                } else if (id == login.getId()) {

                    //계정 있는지 확인하기 위해 서버와 통신 (있으면 checkAccount값이 true로 변경)
                    OkHttpClient client = new OkHttpClient();

                    String url = "http://sassion.kro.kr:8080/sassion/loginCheck.jsp?email=" + inputEmail.getText().toString()
                            + "&password=" + inputPassword.getText().toString();

                    String infoURL = "http://sassion.kro.kr:8080/sassion/getInfo.jsp?email=" + inputEmail.getText().toString();

                    Request request = new Request.Builder()
                            .url(url)
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
                                try {
                                    JSONObject json = new JSONObject(myResponse);
                                    boolean checkAccount = json.getBoolean("check");

                                    //계정이 있을경우 통신
                                    if (checkAccount) {
                                        OkHttpClient infoClient = new OkHttpClient();

                                        Request infoRequest = new Request.Builder()
                                                .url(infoURL)
                                                .get()
                                                .build();

                                        infoClient.newCall(infoRequest).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                e.printStackTrace();
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                if (response.isSuccessful()) {
                                                    final String infoResponse = response.body().string();

                                                    try {
                                                        JSONObject infoJson = new JSONObject(infoResponse);

                                                        //받은 JSON데이터를 맞는 자료로 가공
                                                        String email = infoJson.getString("email");
                                                        String name = infoJson.getString("name");
                                                        String imgUrl = infoJson.getString("imgUrl");


                                                        String challengeName = infoJson.getString("challengeName");
                                                        int challengeDiff = Integer.parseInt(infoJson.getString("challengeDiff"));
                                                        String challengeInfo = infoJson.getString("challengeInfo");
                                                        int challengeStatus = Integer.parseInt(infoJson.getString("challengeStatus"));
                                                        boolean challengeSuccess = "1".equals(infoJson.getString("challengeSuccess"));
                                                        String challengeDate = infoJson.getString("challengeDate");
                                                        int challengeImg = Integer.parseInt(infoJson.getString("challengeImg"));

                                                        int DAY1 = Integer.parseInt(infoJson.getString("DAY1"));
                                                        int DAY2 = Integer.parseInt(infoJson.getString("DAY2"));
                                                        int DAY3 = Integer.parseInt(infoJson.getString("DAY3"));
                                                        int DAY4 = Integer.parseInt(infoJson.getString("DAY4"));
                                                        int DAY5 = Integer.parseInt(infoJson.getString("DAY5"));
                                                        int DAY6 = Integer.parseInt(infoJson.getString("DAY6"));
                                                        int DAY7 = Integer.parseInt(infoJson.getString("DAY7"));
                                                        int DAY8 = Integer.parseInt(infoJson.getString("DAY8"));
                                                        int DAY9 = Integer.parseInt(infoJson.getString("DAY9"));
                                                        int DAY10 = Integer.parseInt(infoJson.getString("DAY10"));
                                                        int DAY11 = Integer.parseInt(infoJson.getString("DAY11"));
                                                        int DAY12 = Integer.parseInt(infoJson.getString("DAY12"));
                                                        int DAY13 = Integer.parseInt(infoJson.getString("DAY13"));
                                                        int DAY14 = Integer.parseInt(infoJson.getString("DAY14"));
                                                        int DAY15 = Integer.parseInt(infoJson.getString("DAY15"));
                                                        int DAY16 = Integer.parseInt(infoJson.getString("DAY16"));
                                                        int DAY17 = Integer.parseInt(infoJson.getString("DAY17"));
                                                        int DAY18 = Integer.parseInt(infoJson.getString("DAY18"));
                                                        int DAY19 = Integer.parseInt(infoJson.getString("DAY19"));
                                                        int DAY20 = Integer.parseInt(infoJson.getString("DAY20"));
                                                        int DAY21 = Integer.parseInt(infoJson.getString("DAY21"));
                                                        int DAY22 = Integer.parseInt(infoJson.getString("DAY22"));
                                                        int DAY23 = Integer.parseInt(infoJson.getString("DAY23"));
                                                        int DAY24 = Integer.parseInt(infoJson.getString("DAY24"));
                                                        int DAY25 = Integer.parseInt(infoJson.getString("DAY25"));
                                                        int DAY26 = Integer.parseInt(infoJson.getString("DAY26"));
                                                        int DAY27 = Integer.parseInt(infoJson.getString("DAY27"));
                                                        int DAY28 = Integer.parseInt(infoJson.getString("DAY28"));
                                                        int DAY29 = Integer.parseInt(infoJson.getString("DAY29"));
                                                        int DAY30 = Integer.parseInt(infoJson.getString("DAY30"));


                                                        int money = Integer.parseInt(infoJson.getString("money"));
                                                        boolean item1 = "1".equals(infoJson.getString("item1"));
                                                        boolean item2 = "1".equals(infoJson.getString("item2"));
                                                        boolean item3 = "1".equals(infoJson.getString("item3"));
                                                        boolean item4 = "1".equals(infoJson.getString("item4"));
                                                        boolean item5 = "1".equals(infoJson.getString("item5"));
                                                        boolean item6 = "1".equals(infoJson.getString("item6"));
                                                        boolean item7 = "1".equals(infoJson.getString("item7"));
                                                        boolean item8 = "1".equals(infoJson.getString("item8"));
                                                        boolean item9 = "1".equals(infoJson.getString("item9"));
                                                        boolean item0 = "1".equals(infoJson.getString("item0"));

                                                        boolean item1mounting = "1".equals(infoJson.getString("item1mounting"));
                                                        boolean item2mounting = "1".equals(infoJson.getString("item2mounting"));
                                                        boolean item3mounting = "1".equals(infoJson.getString("item3mounting"));
                                                        boolean item4mounting = "1".equals(infoJson.getString("item4mounting"));
                                                        boolean item5mounting = "1".equals(infoJson.getString("item5mounting"));
                                                        boolean item6mounting = "1".equals(infoJson.getString("item6mounting"));
                                                        boolean item7mounting = "1".equals(infoJson.getString("item7mounting"));
                                                        boolean item8mounting = "1".equals(infoJson.getString("item8mounting"));
                                                        boolean item9mounting = "1".equals(infoJson.getString("item9mounting"));
                                                        boolean item0mounting = "1".equals(infoJson.getString("item0mounting"));

                                                        //위 데이터를 앱에 저장
                                                        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                                        SharedPreferences.Editor editUserData = userData.edit();

                                                        editUserData.putString("email", email);
                                                        editUserData.putString("name", name);
                                                        editUserData.putString("imgUrl", imgUrl);

                                                        editUserData.putString("challengeName", challengeName);
                                                        editUserData.putInt("challengeDiff", challengeDiff);
                                                        editUserData.putString("challengeInfo", challengeInfo);
                                                        editUserData.putInt("challengeStatus", challengeStatus);
                                                        editUserData.putBoolean("challengeSuccess", challengeSuccess);
                                                        editUserData.putString("challengeDate", challengeDate);
                                                        editUserData.putInt("challengeImg", challengeImg);

                                                        editUserData.putInt("DAY1", DAY1);
                                                        editUserData.putInt("DAY2", DAY2);
                                                        editUserData.putInt("DAY3", DAY3);
                                                        editUserData.putInt("DAY4", DAY4);
                                                        editUserData.putInt("DAY5", DAY5);
                                                        editUserData.putInt("DAY6", DAY6);
                                                        editUserData.putInt("DAY7", DAY7);
                                                        editUserData.putInt("DAY8", DAY8);
                                                        editUserData.putInt("DAY9", DAY9);
                                                        editUserData.putInt("DAY10", DAY10);
                                                        editUserData.putInt("DAY11", DAY11);
                                                        editUserData.putInt("DAY12", DAY12);
                                                        editUserData.putInt("DAY13", DAY13);
                                                        editUserData.putInt("DAY14", DAY14);
                                                        editUserData.putInt("DAY15", DAY15);
                                                        editUserData.putInt("DAY16", DAY16);
                                                        editUserData.putInt("DAY17", DAY17);
                                                        editUserData.putInt("DAY18", DAY18);
                                                        editUserData.putInt("DAY19", DAY19);
                                                        editUserData.putInt("DAY20", DAY20);
                                                        editUserData.putInt("DAY21", DAY21);
                                                        editUserData.putInt("DAY22", DAY22);
                                                        editUserData.putInt("DAY23", DAY23);
                                                        editUserData.putInt("DAY24", DAY24);
                                                        editUserData.putInt("DAY25", DAY25);
                                                        editUserData.putInt("DAY26", DAY26);
                                                        editUserData.putInt("DAY27", DAY27);
                                                        editUserData.putInt("DAY28", DAY28);
                                                        editUserData.putInt("DAY29", DAY29);
                                                        editUserData.putInt("DAY30", DAY30);


                                                        editUserData.putInt("money", money);
                                                        editUserData.putBoolean("item1", item1);
                                                        editUserData.putBoolean("item2", item2);
                                                        editUserData.putBoolean("item3", item3);
                                                        editUserData.putBoolean("item4", item4);
                                                        editUserData.putBoolean("item5", item5);
                                                        editUserData.putBoolean("item6", item6);
                                                        editUserData.putBoolean("item7", item7);
                                                        editUserData.putBoolean("item8", item8);
                                                        editUserData.putBoolean("item9", item9);
                                                        editUserData.putBoolean("item0", item0);

                                                        editUserData.putBoolean("item1mounting", item1mounting);
                                                        editUserData.putBoolean("item2mounting", item2mounting);
                                                        editUserData.putBoolean("item3mounting", item3mounting);
                                                        editUserData.putBoolean("item4mounting", item4mounting);
                                                        editUserData.putBoolean("item5mounting", item5mounting);
                                                        editUserData.putBoolean("item6mounting", item6mounting);
                                                        editUserData.putBoolean("item7mounting", item7mounting);
                                                        editUserData.putBoolean("item8mounting", item8mounting);
                                                        editUserData.putBoolean("item9mounting", item9mounting);
                                                        editUserData.putBoolean("item0mounting", item0mounting);

                                                        //위 정보를 저장이 성공적으로 되면 if문 실행
                                                        if (editUserData.commit()) {
                                                            startActivity(moveFirst);
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                } else {
                                                    System.out.println("Error: " + response.code());
                                                }
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                warningView.setTextColor(Color.RED);
                                                warningView.setText("없는 계정이거나, 비밀번호가 틀립니다");
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Error: " + response.code());
                            }
                        }
                    });


                } else if (id == findPassword.getId()) {
                    startActivity(moveFindPassword);
                } //다른 if문
            }
        };
        login.setOnClickListener(click);
        findPassword.setOnClickListener(click);
        register.setOnClickListener(click);
    }
}