package com.example.sassion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class help extends AppCompatActivity {
    private Button back;
    private JSONArray dataArray;
    private OkHttpClient client = new OkHttpClient();
    private int currentIndex = 0;

    private LinearLayout dataLayout;
    private LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        back = findViewById(R.id.back);
        dataLayout = findViewById(R.id.dataLayout);
        contentLayout = findViewById(R.id.contentLayout);

        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String email = userData.getString("email", "");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivity(intent);
            }
        });

        final EditText title = findViewById(R.id.title);
        final EditText content = findViewById(R.id.content);
        Button submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    String email = userData.getString("email", "");
                    // JSON 데이터 생성
                    JSONObject json = new JSONObject();

                    String url = "http://sassion.kro.kr:8080/sassion/help_write.jsp?tit=" + title.getText().toString()
                            + "&content=" + content.getText().toString() + "&emails=" + email;

                    Request request = new Request.Builder()
                            .url(url)
                            .get()
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            Log.e("NetworkError", "Network request failed.");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                if (response.isSuccessful()) {
                                    final String myResponse = response.body().string();
                                    Log.d("ServerResponse", myResponse);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            handleServerResponse(myResponse);
                                        }
                                    });
                                } else {
                                    Log.e("ServerError", "Error: " + response.code());
                                    System.out.println("Error: " + response.code());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        new GetDataFromJSP().execute();

        dataLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentLayout.getVisibility() == View.VISIBLE) {
                    contentLayout.setVisibility(View.GONE);
                } else {
                    contentLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private String getResponseByIndex(int index) {
        try {
            if (dataArray != null && index >= 0 && index < dataArray.length()) {
                JSONObject currentObject = dataArray.getJSONObject(index);
                if (currentObject.has("response")) {
                    return currentObject.getString("response");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("GetResponseByIndex", "JSONException 발생: " + e.getMessage());
        }
        return "내용을 확인 중에 있습니다.";
    }

    private void showMiniDialog(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(content);
        builder.setPositiveButton("확인", null);
        builder.show();
    }

    private void handleServerResponse(String myResponse) {
        showThankYouImage();
    }

    // 감사 이미지를 보여주는 다이얼로그
    private void showThankYouImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.thank_you_dialog, null);
        builder.setView(dialogView);


        builder.setPositiveButton("확인", (dialog, which) -> {
            Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
            startActivity(intent);
        });

        builder.show();
    }

    private class GetDataFromJSP extends AsyncTask<Void, Void, List<DataItem>> {
        @Override
        protected List<DataItem> doInBackground(Void... voids) {
            List<DataItem> matchingItems = new ArrayList<>();

            try {
                Request request = new Request.Builder()
                        .url("http://sassion.kro.kr:8080/sassion/help_read.jsp")
                        .get()
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (result != null) {
                        dataArray = new JSONArray(result);

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject nextObject = dataArray.getJSONObject(i);

                            if (nextObject.has("email") || nextObject.has("emails")) {
                                String currentEmail = nextObject.optString("emails", "");

                                SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                String email = userData.getString("email", "");

                                if (email.equals(currentEmail)) {
                                    String title = nextObject.optString("title", "");
                                    String content = nextObject.optString("content", "");
                                    String responses = nextObject.optString("response", "");

                                    currentIndex = i;

                                    matchingItems.add(new DataItem(title, content, responses));
                                }
                            } else {
                                Log.e("GetDataFromJSP", "email 또는 emails가 없는 데이터가 있습니다. 인덱스: " + i);
                            }
                        }
                    }
                } else {
                    Log.e("GetDataFromJSP", "결과가 null로 수신되었습니다");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("GetDataFromJSP", "JSONException 발생: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("GetDataFromJSP", "예외 발생: " + e.getMessage());
            }

            return matchingItems;
        }

        @Override
        protected void onPostExecute(List<DataItem> matchingItems) {
            if (!matchingItems.isEmpty()) {
                for (int i = 0; i < matchingItems.size(); i++) {
                    DataItem item = matchingItems.get(i);
                    TextView titleTextView = createTitleTextView(item.getTitle(), item.getContent(), item.getResponse());
                    dataLayout.addView(titleTextView);
                }

                contentLayout.setVisibility(View.VISIBLE);
            } else {
                Log.e("GetDataFromJSP", "일치하는 데이터가 없습니다.");
            }
        }

        private TextView createTitleTextView(String title, String content, String response) {
            TextView titleTextView = new TextView(help.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 40, 0, 40);
            titleTextView.setLayoutParams(params);
            titleTextView.setText("제목: " + title + "\n문의 내용: " + content);
            titleTextView.setTextSize(12);
            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMiniDialog(response);
                }
            });

            return titleTextView;
        }
    }

    private static class DataItem {
        private String title;
        private String content;
        private String response;

        public DataItem(String title, String content, String response) {
            this.title = title;
            this.content = content;
            this.response = response;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getResponse() {
            return response;
        }
    }
}
