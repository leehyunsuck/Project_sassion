package com.example.sassion;

import static com.example.sassion.SendMail.sendmail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Register extends AppCompatActivity {
    private int code;
    private String saveEmail;
    private String saveName;
    private boolean boolCheck = false;
    private boolean boolSendMail = false;
    final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //인증번호 생성 관련 랜덤객체 생성
        Random rand = new Random();

        //위젯 선언
        EditText inputName = findViewById(R.id.inputName);
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputCode = findViewById(R.id.inputCode);
        EditText inputPassword = findViewById(R.id.inputPassword);
        EditText inputRePassword = findViewById(R.id.inputRePassword);

        TextView warningMsg = findViewById(R.id.warningMsg);

        Button sendMail = findViewById(R.id.sendMail);
        Button checkCode = findViewById(R.id.checkCode);
        Button checkRegister = findViewById(R.id.registerButton);
        Button back = findViewById(R.id.backButton);

        ImageView userPhoto = findViewById(R.id.userPhoto);

        Intent movePage = new Intent(this, Tutorial.class);
        //Intent movePage = new Intent(this, MainActivity.class);


        //클릭기능
        View.OnClickListener click = new View.OnClickListener() {
            public void onClick(View view) {
                int id = view.getId();

                //클라이언트 통신 객체 생성
                OkHttpClient client = new OkHttpClient();

                //인증번호 보내기 버튼 클릭
                if (id == sendMail.getId()) {
                    //숫자, 한글, 알파벳, !, #, @, . 외에 값 제거
                    inputEmail.setText(inputEmail.getText().toString().replaceAll("[^가-힣a-zA-Z@!#\\\\.0-9]", ""));
                    inputName.setText(inputName.getText().toString().replaceAll("[^가-힣a-zA-Z@!#\\\\.0-9]", ""));

                    //이메일 비어있을 시
                    if (inputEmail.getText().toString().isEmpty()) {
                        warningMsg.setTextColor(Color.RED);
                        warningMsg.setText("이메일을 입력해주세요");

                    //닉네임 비어있을 시
                    } else if (inputName.getText().toString().isEmpty()) {
                        warningMsg.setTextColor(Color.RED);
                        warningMsg.setText("닉네임을 입력해주세요");

                    //닉네임 길이가 조건에 맞지 않을 시
                    } else if (inputName.getText().toString().length() < 3 || inputName.getText().toString().length() > 12) {
                        warningMsg.setTextColor(Color.RED);
                        warningMsg.setText("닉네임의 길이는 3글자 이상, 6글자 이하 제한입니다");

                    //모든 조건 성립시
                    } else {
                        //값 저장해놓기
                        saveEmail = inputEmail.getText().toString();
                        saveName = inputName.getText().toString();

                        //사용가능 이메일인지 확인
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

                                        //이메일이 서버에 없는 데이터일경우 가입 가능 -> 인증번호 전송
                                        if (check) {
                                            code = sendmail(inputEmail.getText().toString());
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    warningMsg.setTextColor(Color.GREEN);
                                                    warningMsg.setText("이메일 전송 완료");
                                                    if (boolSendMail) {
                                                        warningMsg.setTextColor(Color.GREEN);
                                                        warningMsg.setText("인증번호를 재전송하였습니다");
                                                    }
                                                    boolSendMail = true;
                                                }
                                            });
                                        //이메일이 서버 데이터에 이미 등록되어있는 이메일임
                                        } else {
                                            System.out.println("값 있음 사용 불가능");
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    warningMsg.setTextColor(Color.RED);
                                                    warningMsg.setText("이미 있는 이메일입니다");
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

                        if (code == userCode) {
                            warningMsg.setTextColor(Color.GREEN);
                            warningMsg.setText("인증완료");
                            boolCheck = true;
                        } else {
                            warningMsg.setTextColor(Color.RED);
                            warningMsg.setText("인증번호가 다릅니다. 다시 확인해주세요");
                            inputCode.setText("");
                        }
                    } catch (Exception e){
                        warningMsg.setTextColor(Color.RED);
                        warningMsg.setText("인증번호를 제대로 입력하세요");
                        inputCode.setText("");
                    }
                } else if (id == checkRegister.getId()) {
                    if (boolCheck) {
                        if (saveEmail.equals(inputEmail.getText().toString())) {
                            if (saveName.equals(inputName.getText().toString())) {
                                if (inputRePassword.getText().toString().equals(inputPassword.getText().toString())) {
                                    if (inputPassword.getText().toString().length() < 6 || inputPassword.getText().toString().length() > 12) {
                                        warningMsg.setTextColor(Color.RED);
                                        warningMsg.setText("비밀번호는 6자 이상, 12자 이하로 바꿔주세요");
                                    } else {
                                        int passwordLength = inputPassword.getText().toString().length();
                                        inputPassword.setText(inputPassword.getText().toString().replaceAll("[^a-zA-Z@!#0-9]", ""));
                                        if (passwordLength == inputPassword.getText().toString().length()) {
                                            String url = "http://sassion.kro.kr:8080/sassion/register.jsp?email=" + inputEmail.getText().toString()
                                                    + "&password=" + inputPassword.getText().toString()
                                                    + "&name=" + inputName.getText().toString();

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
                                                            String registerCheck = json.getString("check");
                                                            String registerMsg = "";
                                                            int registerMsgColor = Color.RED;

                                                            //회원가입 성공
                                                            if (registerCheck.equals("true")) {
                                                                String infoURL = "http://sassion.kro.kr:8080/sassion/getInfo.jsp?email=" + inputEmail.getText().toString();

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
                                                                                    activity.runOnUiThread(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            Toast.makeText(activity, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    });
                                                                                    startActivity(movePage);
                                                                                }
                                                                            } catch (Exception e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        } else {
                                                                            System.out.println("Error: " + response.code());
                                                                        }
                                                                    }
                                                                });
                                                                finish();
                                                            } else if (registerCheck.equals("false")) {
                                                                registerMsg = "이미 있는 계정입니다";
                                                            } else if (registerCheck.equals("nullError")) {
                                                                registerMsg = "Null에러가 발생하였습니다";
                                                            } else if (registerCheck.equals("error")) {
                                                                registerMsg = "서버에 접속 불가능 (잠시 후 시도해주세요)";
                                                            } else {
                                                                registerMsg = "Error";
                                                            }
                                                            //왜 이렇게 해야하는지 모르겠음
                                                            String finalRegisterMsg = registerMsg;
                                                            int finalRegisterMsgColor = registerMsgColor;

                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    warningMsg.setTextColor(finalRegisterMsgColor);
                                                                    warningMsg.setText(finalRegisterMsg);
                                                                }
                                                            });

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    } else {
                                                        warningMsg.setTextColor(Color.RED);
                                                        warningMsg.setText("Error: " + response.code());
                                                    }
                                                }
                                            });
                                        } else {
                                            //비밀번호는 숫자, 알파벳, #, @, ! 만 입력 가능
                                            warningMsg.setTextColor(Color.RED);
                                            warningMsg.setText("비밀번호는 `숫자, 알파벳, #, @, !` 만 입력 가능합니다");
                                        }
                                    }
                                } else {
                                    //비밀번호 재입력값이 다를 때
                                    warningMsg.setTextColor(Color.RED);
                                    warningMsg.setText("비밀번호입력과 재입력 값이 서로 달라요");
                                }
                            } else {
                                //닉네임이 인증하였을때와 달라요. 다시 인증해주세요
                                warningMsg.setTextColor(Color.RED);
                                warningMsg.setText("이런! 닉네임을 인증 후 바꾸셨군요? 다시 메일인증 해주세요");
                                boolCheck = false;
                            }
                        } else {
                            warningMsg.setTextColor(Color.RED);
                            warningMsg.setText("이런! 이메일을 인증 후 바꾸셨군요? 다시 메일인증 해주세요");
                            boolCheck = false;
                        }
                    }
                } else if (id == back.getId()) {
                    finish();
                }   //다른 버튼 작동 else if문 작성
            }
        };
        sendMail.setOnClickListener(click);
        checkCode.setOnClickListener(click);
        checkRegister.setOnClickListener(click);
        back.setOnClickListener(click);

        //사진기능
        View.OnClickListener photo = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningMsg.setTextColor(Color.RED);
                warningMsg.setText("이미지 서비스는 업데이트중입니다");
            }
        };
        userPhoto.setOnClickListener(photo);
    }
}