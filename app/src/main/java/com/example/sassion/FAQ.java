package com.example.sassion;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.ViewGroup; // 추가된 부분
import android.view.ViewTreeObserver; // 추가된 부분
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FAQ extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        Button backButton = findViewById(R.id.back_bt);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivity(intent);
            }
        });
    }

//    public class FAQItem {
//        private String title;
//        private String content;
//
//        public FAQItem(String title, String content) {
//            this.title = title;
//            this.content = content;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public String getContent() {
//            return content;
//        }
//    }
//
//    private ArrayList<FAQItem> faqItems = new ArrayList<>();
//    private OkHttpClient client = new OkHttpClient();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_faq);
//
//        Button backButton = findViewById(R.id.back_bt);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // 초기 데이터를 가져오는 AsyncTask 실행
//        new GetDataFromJSP().execute();
//    }
//
//    class GetDataFromJSP extends AsyncTask<Void, Void, String> {
//        @Override
//        protected String doInBackground(Void... voids) {
//            String url = "http://sassion.kro.kr:8080/sassion/faq_read.jsp";
//
//            try {
//                Request request = new Request.Builder()
//                        .url(url)
//                        .get()
//                        .build();
//
//                Response response = client.newCall(request).execute();
//
//                if (response.isSuccessful()) {
//                    return response.body().string();
//                }
//                if (!response.isSuccessful()) {
//                    Log.e("FAQ", "Server error, code: " + response.code());
//                    Log.e("FAQ", "Server error, body: " + response.body().string());
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e("FAQ", "IOException: " + e.getMessage());
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                try {
//                    JSONArray jsonArray = new JSONArray(result);
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String title = jsonObject.getString("title");
//                        String content = jsonObject.getString("content");
//
//                        FAQItem faqItem = new FAQItem(title, content);
//                        faqItems.add(faqItem);
//                    }
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            updateUI();
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.e("FAQ", "IOException: " + e.getMessage());
//                }
//            } else {
//                // 결과가 null인 경우 처리
//                Log.e("FAQ", "Result is null");
//            }
//        }
//    }
//
//    private void updateUI() {
//        // ScrollView의 LinearLayout 가져오기
//        final LinearLayout scrollLinear = findViewById(R.id.scrollLinear);
//
//        // FAQ 항목을 동적으로 추가하기
//        for (int i = 0; i < faqItems.size(); i++) {
//            final FAQItem faqItem = faqItems.get(i);
//
//            // 새로운 TextView 생성
//            TextView textView = new TextView(this);
//            textView.setId(View.generateViewId()); // 동적으로 고유한 ID 생성
//            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//
//
//            // TextView에 동적으로 ID 설정
//            if (i == 0 || i == 1) {
//                int resID = getResources().getIdentifier("faq" + (i + 1), "id", getPackageName());
//                textView.setId(resID);
//            } else {
//                textView.setId(View.generateViewId());
//            }
//
//            // TextView에 내용 설정
//            textView.setText("제목: " + faqItem.getTitle() + "\n내용: " + faqItem.getContent());
//
//            // 인덱스가 0 또는 1인 경우 바깥의 TextView에 추가
//            if (i == 0) {
//                TextView faq1TextView = findViewById(R.id.faq1);
//                faq1TextView.setText(textView.getText());
//            } else if (i == 1) {
//                TextView faq2TextView = findViewById(R.id.faq2);
//                faq2TextView.setText(textView.getText());
//            }
//
//            // 인덱스가 2 이상인 경우 ScrollView의 LinearLayout에 추가
//            if (i >= 2) {
//                scrollLinear.addView(textView);
//
//                // 각 항목 사이에 구분선을 추가할 수도 있습니다.
//                if (i < faqItems.size() - 1) {
//                    View separator = new View(this);
//                    separator.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            1 // 선의 높이
//                    );
//                    layoutParams.setMargins(0, 10, 0, 10); // 선과 텍스트 간의 간격 조절
//                    separator.setLayoutParams(layoutParams);
//
//                    // separator를 scrollLinear에 추가
//                    scrollLinear.addView(separator);
//                }
//            }
//        }
//    }

}
