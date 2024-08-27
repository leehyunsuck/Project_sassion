package com.example.sassion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class OptionActivity extends HomeActivity {

    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        // 뒤로가기 버튼 누르면 메인 화면으로 가기
        Button moveButton = findViewById(R.id.back_option);
        moveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v1) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });


        aSwitch = (Switch) findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getApplicationContext(),"알림 수신이 완료되었습니다.",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"알림 수신이 차단되었습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });

        //앱정보
        Button appInfo = (Button) findViewById(R.id.button_introduction);
        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), appinfo.class);
                startActivity(intent);
            }
        });

        Button notice = (Button) findViewById(R.id.button_announcement);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), notice.class);
                startActivity(intent);
            }
        });

        Button help = (Button) findViewById(R.id.button_Contact);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), help.class);
                startActivity(intent);
            }
        });

        Button faq = (Button) findViewById(R.id.button_faq);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FAQ.class);
                startActivity(intent);
            }
        });

        Button myinfo = (Button) findViewById(R.id.button_myinfo);
        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), myinfo.class);  //변경
                startActivity(intent);
            }
        });

        //관리자 페이지 이동
        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String email = userData.getString("email", "");

        Button manager = findViewById(R.id.button_manager);

        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();

                String infoURL = "http://sassion.kro.kr:8080/sassion/getaccess.jsp?email=" + email;

                Request request = new Request.Builder()
                        .url(infoURL)
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
                                int accessLevel = json.getInt("access");

                                if (accessLevel == 2) {
                                    Intent intent = new Intent(getApplicationContext(), manager.class);
                                    startActivity(intent);
                                }

                            } catch (JSONException e) {
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
