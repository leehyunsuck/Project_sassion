package com.example.sassion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.sassion.SendMail.sendmail;

public class ChangeInfo extends AppCompatActivity {

    static int code;
    static boolean check = false;
    static boolean passwordCheck = false;
    static String saveName = "";
    static String savePassword = "";
    static boolean sendMailWait = false;
    private static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        EditText inputName = findViewById(R.id.inputName);
        EditText inputPassword = findViewById(R.id.inputPassword);
        EditText inputCode = findViewById(R.id.inputCode);
        EditText inputPasswordDelete = findViewById(R.id.inputPasswordDelete);

        TextView viewEmail = findViewById(R.id.viewEmail);

        Button sendMail = findViewById(R.id.sendMail);
        Button checkCode = findViewById(R.id.checkCode);
        Button change = findViewById(R.id.change);
        Button checkPassword = findViewById(R.id.checkPassword);
        Button remove = findViewById(R.id.remove);
        Button back = findViewById(R.id.back);

        Intent moveLogin = new Intent(this, Login.class);

        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String email = userData.getString("email", "");
        String name = userData.getString("name", "");


        viewEmail.setText(email);
        inputName.setText(name);

        OkHttpClient tclient = new OkHttpClient();

        String turl = "http://sassion.kro.kr:8080/sassion/getPassword.jsp?email=" + email + "&privateCode=10310930";

        Request request = new Request.Builder()
                .url(turl)
                .get()
                .build();

        tclient.newCall(request).enqueue(new Callback() {
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
                        Boolean checkAccount = json.getBoolean("check");

                        if (checkAccount) {
                            password = json.getString("password");
                            inputPassword.setText(json.getString("password"));
                            inputPassword.invalidate();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        Toast.makeText(ChangeInfo.this, "닉네임 또는 비밀번호를 변경한 후 이메일 인증을 진행해주세요", Toast.LENGTH_LONG).show();


        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                if (id == sendMail.getId()) {
                    inputName.setText(inputName.getText().toString().replaceAll("[^가-힣a-zA-Z@!#\\\\.0-9]", ""));

                    String temp = inputPassword.getText().toString().replaceAll("[^a-zA-Z@!#0-9]", "");

                    if (inputName.getText().toString().length() < 3 || inputName.getText().toString().length() > 12) {
                        Toast.makeText(ChangeInfo.this, "닉네임 길이를 3자 이상 6자 이하로 설정해주세요", Toast.LENGTH_SHORT).show();
                    } else if (sendMailWait) {
                        Toast.makeText(ChangeInfo.this, "인증코드 전송 중 입니다", Toast.LENGTH_SHORT).show();
                    } else if (inputPassword.getText().toString().length() < 6 || inputPassword.getText().toString().length() > 12) {
                        Toast.makeText(ChangeInfo.this, "비밀번호를 6자 이상 12자 이하로 설정해주세요", Toast.LENGTH_SHORT).show();
                    } else if (!inputPassword.getText().toString().equals(temp)) {
                        Toast.makeText(ChangeInfo.this, "비밀번호는 영문자 숫자 # @ ! 만 가능합니다", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "이메일 전송 중", Toast.LENGTH_SHORT).show();
                        sendMailWait = true;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                code = sendmail(email);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "이메일 전송 완료", Toast.LENGTH_SHORT).show();
                                        sendMailWait = false;
                                    }
                                });
                            }
                        }).start();
                        saveName = inputName.getText().toString();
                        savePassword = inputPassword.getText().toString();
                    }
                } else if (id == checkCode.getId()) {
                    String msg = "";

                    try {
                        int userCode = Integer.parseInt(inputCode.getText().toString());

                        if (userCode == code) {
                            msg = "인증 완료";
                            check = true;
                        } else {
                            msg = "인증번호 다름";
                        }
                    } catch (Exception e) {
                        msg = "인증번호는 숫자로만 이루어져 있습니다";
                    }
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } else if (id == change.getId()) {
                    if (check) {
                        if (saveName.equals(inputName.getText().toString())) {
                            if (savePassword.equals(inputPassword.getText().toString())) {
                                //변경JSP 작동
                                OkHttpClient changeClient = new OkHttpClient();
                                String changeUrl = "http://sassion.kro.kr:8080/sassion/changePassword.jsp?email="+email+"&name="+saveName+"&password="+savePassword+"&privateCode=19593231";
                                Request changeRequest = new Request.Builder()
                                        .url(changeUrl)
                                        .get()
                                        .build();
                                changeClient.newCall(changeRequest).enqueue(new Callback() {
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
                                                Boolean check = json.getBoolean("check");
                                                if (check) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(ChangeInfo.this, "회원정보 수정 완료", Toast.LENGTH_SHORT).show();
                                                            SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editUserData = userData.edit();
                                                            editUserData.putString("name", saveName);
                                                            editUserData.commit();
                                                            Intent move = new Intent(getApplicationContext(), OptionActivity.class);
                                                            startActivity(move);
                                                        }
                                                    });
                                                } else {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(ChangeInfo.this, "서버 연결 실패", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        } else {
                                            System.out.println("Error: " + response.code());
                                        }
                                    }
                                });
                            } else  {
                                //비밀번호 인증 후 바꿈
                                Toast.makeText(ChangeInfo.this, "인증 후 비밀번호를 수정하였군요? 다시 인증해주세요", Toast.LENGTH_SHORT).show();
                                check = false;
                            }
                        } else {
                            //닉네임 인증 후 바꿈
                            Toast.makeText(ChangeInfo.this, "인증 후 닉네임을 수정하였군요? 다시 인증해주세요", Toast.LENGTH_SHORT).show();
                            check = false;
                        }
                    } else {
                        Toast.makeText(ChangeInfo.this, "이메일 인증을 진행해주세요", Toast.LENGTH_SHORT).show();
                    }
                } else if (id == checkPassword.getId()) {
                    //비밀번호 동일한지 확인
                    AlertDialog.Builder alert = new AlertDialog.Builder(ChangeInfo.this);
                    alert.setTitle("인증번호 확인");
                    alert.setIcon(R.drawable.ic_launcher_background);

                    if (inputPasswordDelete.getText().toString().equals(password)) {
                        passwordCheck = true;
                        alert.setMessage("인증 완료");
                    } else {
                        alert.setMessage("비밀번호를 옳바르게 작성해주세요");
                    }
                    alert.show();
                } else if (id == remove.getId()) {
                    if (check) {
                        if (passwordCheck) {
                            OkHttpClient delClient = new OkHttpClient();
                            String delURL = "http://sassion.kro.kr:8080/sassion/delAccount.jsp?email=" + email + "&privateCode=5589141512";
                            Request delRequest = new Request.Builder()
                                    .url(delURL)
                                    .get()
                                    .build();
                            delClient.newCall(delRequest).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    if (response.isSuccessful()) {
                                        final String delResponce = response.body().string();

                                        try {
                                            JSONObject delJson = new JSONObject(delResponce);
                                            Boolean check = delJson.getBoolean("check");

                                            if (check) {
                                                SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editUserData = userData.edit();
                                                editUserData.clear();
                                                editUserData.commit();
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(ChangeInfo.this, "회원탈퇴 완료", Toast.LENGTH_SHORT).show();
                                                        startActivity(moveLogin);
                                                    }
                                                });
                                            }
                                        } catch (Exception e) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(ChangeInfo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                }
                            });
                            String url2 = "http://sassion.kro.kr:8080/sassion/delhelp.jsp?email=" + email;
                            Request request2 = new Request.Builder()
                                    .url(url2)
                                    .get()
                                    .build();

                            delClient.newCall(request2).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    if (response.isSuccessful()) {
                                        final String myResponse = response.body().string();
                                        System.out.println("응답 본문: " + myResponse);

                                        // Additional processing if needed
                                    } else {
                                        System.out.println("Error: " + response.code());
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(ChangeInfo.this, "비밀번호 인증을 진행해주세요", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChangeInfo.this, "이메일 인증을 진행해주세요", Toast.LENGTH_SHORT).show();
                    }
                } else if (id == back.getId()) {
                    finish();
                }
            }
        };
        sendMail.setOnClickListener(click);
        checkCode.setOnClickListener(click);
        change.setOnClickListener(click);
        checkPassword.setOnClickListener(click);
        remove.setOnClickListener(click);
        back.setOnClickListener(click);
    }
}