package com.example.sassion;

import static com.example.sassion.SendMail.sendmail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
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

public class FindPassword extends AppCompatActivity {
    int code;
    boolean check = false;
    boolean reSend = false;
    String saveEmail;
    final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputCode = findViewById(R.id.inputCode);
        EditText inputPassword = findViewById(R.id.inputPassword);
        EditText inputRePassword = findViewById(R.id.inputRePassword);

        TextView warningMsg = findViewById(R.id.warningMsg);

        Button sendMail = findViewById(R.id.sendMail);
        Button checkCode = findViewById(R.id.checkCode);
        Button changePassword = findViewById(R.id.changePasswordButton);
        Button back = findViewById(R.id.backButton);

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                if (id == back.getId()) {
                    finish();
                } else if (id == sendMail.getId()) {
                    if (inputEmail.getText().toString().isEmpty()) {
                        warningMsg.setTextColor(Color.RED);
                        warningMsg.setText("이메일을 입력해주세요");
                    } else {
                        Toast.makeText(getApplicationContext(), "이메일 전송 중", Toast.LENGTH_SHORT).show();
                        OkHttpClient client = new OkHttpClient();
                        String url = "http://sassion.kro.kr:8080/sassion/registerCheck.jsp?email=" + inputEmail.getText().toString();
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
                                //성공적으로 보냈을 때
                                if (response.isSuccessful()) {
                                    final String myResponse = response.body().string();
                                    try {
                                        JSONObject json = new JSONObject(myResponse);

                                        String temp = json.getString("check");
                                        boolean check = Boolean.parseBoolean(temp);

                                        //이메일이 없을경우
                                        if (check) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    warningMsg.setTextColor(Color.RED);
                                                    warningMsg.setText("등록되지 않은 이메일입니다");

                                                }
                                            });
                                            //이메일이 있을경우
                                        } else {
                                            code = sendmail(inputEmail.getText().toString());
                                            saveEmail = inputEmail.getText().toString();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (reSend) {
                                                        warningMsg.setTextColor(Color.GREEN);
                                                        warningMsg.setText("인증번호 재전송 완료");
                                                        Toast.makeText(getApplicationContext(), "이메일 재전송 완료", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        warningMsg.setTextColor(Color.GREEN);
                                                        warningMsg.setText("인증번호 전송 완료");
                                                        Toast.makeText(getApplicationContext(), "이메일 전송완료", Toast.LENGTH_SHORT).show();
                                                    }
                                                    reSend = true;
                                                }
                                            });
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    warningMsg.setTextColor(Color.RED);
                                    warningMsg.setText("Error: " + response.code());
                                }
                            }
                        });
                    }
                } else if (id == checkCode.getId()) {
                    try {
                        int userCode = Integer.parseInt(inputCode.getText().toString());

                        if (userCode == code) {
                            warningMsg.setTextColor(Color.GREEN);
                            warningMsg.setText("인증 성공");
                            check = true;
                        } else {
                            warningMsg.setTextColor(Color.RED);
                            warningMsg.setText("인증번호가 다릅니다");
                        }
                    } catch (Exception e) {
                        warningMsg.setTextColor(Color.RED);
                        warningMsg.setText("인증번호는 숫자로만 이루어져있습니다");
                    }
                } else if (id == changePassword.getId()) {
                    if (check) {
                        if (inputPassword.length() >= 6 && inputPassword.length() <= 12) {
                            if (saveEmail.equals(inputEmail.getText().toString())) {
                                if (inputPassword.getText().toString().equals(inputRePassword.getText().toString())) {
                                    if (inputPassword.getText().toString().replaceAll("[^a-zA-Z@!#0-9]", "").length() == inputPassword.getText().toString().length()) {
                                        OkHttpClient client = new OkHttpClient();

                                        //URL수정해야함
                                        String url = "http://sassion.kro.kr:8080/sassion/findPassword.jsp?email=" + inputEmail.getText().toString() + "&password=" + inputPassword.getText().toString() + "&privateCode=19593231";

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
                                                        boolean check = json.getBoolean("check");

                                                        if (check) {
                                                            activity.runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(activity, "비밀번호 변경 성공", Toast.LENGTH_SHORT).show();
                                                                    finish();
                                                                }
                                                            });
                                                            finish();
                                                        } else {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    warningMsg.setTextColor(Color.RED);
                                                                    warningMsg.setText("비밀번호 수정 실패");
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
                                    } else {
                                        //허용하지 않는 문자 들어감
                                        warningMsg.setTextColor(Color.RED);
                                        warningMsg.setText("비밀번호는 영문자, 숫자, @, !, #만 입력 가능합니다");
                                    }
                                } else {
                                    //비밀번호 입력값이 다름
                                    warningMsg.setTextColor(Color.RED);
                                    warningMsg.setText("비밀번호 입력값이 서로 다릅니다");
                                }
                            } else {
                                //이메일 바꿈 재인증 해야함
                                check = false;
                                warningMsg.setTextColor(Color.RED);
                                warningMsg.setText("인증하였던 이메일이 아닙니다. 이메일 인증을 다시 해주세요");
                            }
                        } else {
                            //비번 길이
                            warningMsg.setTextColor(Color.RED);
                            warningMsg.setText("비밀번호는 6자 이상, 12자 이하로 수정해주세요");
                        }
                    } else {
                        //인증 안함
                        warningMsg.setTextColor(Color.RED);
                        warningMsg.setText("이메일 인증을 먼저 해주세요");
                    }
                }
            }
        };
        sendMail.setOnClickListener(click);
        checkCode.setOnClickListener(click);
        changePassword.setOnClickListener(click);
    }
}