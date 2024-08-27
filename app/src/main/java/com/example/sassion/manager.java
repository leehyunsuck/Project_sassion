package com.example.sassion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class manager extends AppCompatActivity {

    public class ManagerItem {
        private String title;
        private String content;
        private String response;

        public ManagerItem(String title, String content, String response) {
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

    private ArrayList<ManagerItem> noticeItems = new ArrayList<>();
    private ArrayList<ManagerItem> helpItems = new ArrayList<>();
    private OkHttpClient client = new OkHttpClient();
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        Button backButton = findViewById(R.id.back_bt);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivity(intent);
            }
        });
        loadHelpData();

    }




    private void loadHelpData() {
        Request request = new Request.Builder()
                .url("http://sassion.kro.kr:8080/sassion/help_read.jsp")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("manager", "Help Data Loading Failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String helpResult = response.body().string();
                        updateItemList(helpItems, new JSONArray(helpResult));
                        runOnUiThread(() -> updateHelpUI());
                    } else {
                        Log.e("manager", "Help Data Loading Failed: " + response.code());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("manager", "JSONException: " + e.getMessage());
                } finally {
                    if (response != null) {
                        response.close();
                    }
                }
            }
        });
    }


    private void updateItemList(ArrayList<ManagerItem> itemList, JSONArray jsonArray) {
        itemList.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String title = jsonObject.optString("tit", jsonObject.optString("title", ""));
                String content = jsonObject.optString("content", "");
                String response = jsonObject.optString("response", "");

                ManagerItem item = new ManagerItem(title, content, response);
                itemList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("manager", "JSONException: " + e.getMessage());
        }
    }

    private void updateHelpUI() {
        ScrollView scrollView = findViewById(R.id.scrollView2);
        final LinearLayout linearLayout = findViewById(R.id.scrollLinear2);

        runOnUiThread(() -> {
            linearLayout.removeAllViews();

            for (int i = 0; i < helpItems.size(); i++) {
                final ManagerItem helpItem = helpItems.get(i);

                TextView textView = new TextView(manager.this);
                textView.setText("제목: " + helpItem.title + "\n내용: " + helpItem.content);
                textView.setTextSize(12);

                textView.setOnClickListener(v -> showHelpDetailDialog(helpItem.title, helpItem.content, helpItem.response));

                linearLayout.addView(textView);

                if (i < helpItems.size() - 1) {
                    addSeparator(linearLayout);
                }
            }
        });
    }

    private void showHelpDetailDialog(final String title, final String content, final String response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("도움말 내용");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView textTitle = new TextView(this);
        textTitle.setText("제목: " + title);

        TextView textContent = new TextView(this);
        textContent.setText("내용: " + content);

        TextView textResponse = new TextView(this);
        textResponse.setText("응답: " + response);

        layout.addView(textTitle);
        layout.addView(textContent);
        layout.addView(textResponse);

        builder.setView(layout);

        builder.setPositiveButton("수정", (dialog, which) -> showEditHelpDialog(title, response));

        builder.setNegativeButton("닫기", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showEditHelpDialog(final String title, final String response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("도움말 답변");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputResponse = new EditText(this);
        inputResponse.setText(response);
        inputResponse.setInputType(InputType.TYPE_CLASS_TEXT);

        layout.addView(inputResponse);

        builder.setView(layout);

        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());

        builder.setPositiveButton("수정 완료", (dialog, which) -> updateHelpResponse(title, response, inputResponse.getText().toString()));

        builder.show();
    }

    private void updateHelpResponse(final String title, final String originalResponse, final String updatedResponse) {
        String url = "http://sassion.kro.kr:8080/sassion/help_update.jsp";
        String fullUrl = url + "?title=" + title + "&originalResponse=" + originalResponse + "&updatedResponse=" + updatedResponse;

        Request request = new Request.Builder()
                .url(fullUrl)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        runOnUiThread(() -> {
                            Toast.makeText(manager.this, "도움말이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                        });
                    } else {

                        runOnUiThread(() -> {
                            Log.e("manager", "Server error, code: " + response.code());
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 반드시 응답을 닫아야 합니다.
                    if (response != null) {
                        response.close();
                    }
                }
            }
        });
    }

    private void addSeparator(LinearLayout linearLayout) {
        View separator = new View(this);
        separator.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        layoutParams.setMargins(0, 10, 0, 10);
        separator.setLayoutParams(layoutParams);
        linearLayout.addView(separator);
    }

}

