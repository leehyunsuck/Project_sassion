package com.example.sassion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Shop extends AppCompatActivity {
    private int itemCount = 10;
    private boolean[] itemHave = new boolean[itemCount];
    private boolean[] itemMounting = new boolean[itemCount];
    private String[] itemNames = {"전등", "컵", "액자", "바나나", "사과"};
    private int[] itemImgs = {R.drawable.item_light_v, R.drawable.item_cup_v,
                                R.drawable.item_photo_frame_v, R.drawable.item_banana_v, R.drawable.item_apple_v};
    private int[] itemPrices = {100, 10, 100, 50, 10};
    private int money;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Button back = findViewById(R.id.backButton);
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                if (id == back.getId()) {
                    Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(home);
                }

            }
        };
        back.setOnClickListener(click);

        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editUserData = userData.edit();

        money = userData.getInt("money", 0);

        TextView viewMoney = findViewById(R.id.viewMoney);
        viewMoney.setText(String.valueOf(money));

        for (int i = 0; i < itemCount; i++) {
            itemHave[i] = userData.getBoolean("item"+i, false);
            itemMounting[i] = userData.getBoolean("item"+i+"mounting", false);
        }

        LinearLayout container = findViewById(R.id.scrollLinear);

        for (int i = 0; i < 5; i++) {
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.VERTICAL);

            TextView nameTextView = new TextView(this);
            nameTextView.setText("품목 : " + itemNames[i]);
            itemLayout.addView(nameTextView);

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(itemImgs[i]);
            itemLayout.addView(imageView);

            TextView priceTextView = new TextView(this);
            priceTextView.setText("가격: " + itemPrices[i]);
            itemLayout.addView(priceTextView);

            Button button = new Button(this);
            updateButtonLabel(button, i);
            itemLayout.addView(button);

            TextView temp = new TextView(this);
            temp.setText("\n\n");
            itemLayout.addView(temp);

            // 버튼에 클릭 이벤트 처리 추가
            final int itemIndex = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭 이벤트 처리
                    if (itemHave[itemIndex]) {
                        if (itemMounting[itemIndex]) {
                            itemMounting[itemIndex] = false;
                            editUserData.putBoolean("item"+itemIndex+"mounting", itemMounting[itemIndex]);
                            editUserData.putBoolean("item"+itemIndex, itemHave[itemIndex]);
                            editUserData.commit();
                            updateButtonLabel(button, itemIndex); // 버튼 레이블 업데이트
                        } else {
                            itemMounting[itemIndex] = true;
                            editUserData.putBoolean("item"+itemIndex+"mounting", itemMounting[itemIndex]);
                            editUserData.putBoolean("item"+itemIndex, itemHave[itemIndex]);
                            editUserData.commit();
                            updateButtonLabel(button, itemIndex); // 버튼 레이블 업데이트
                        }
                    } else {
                        if (purchaseItem(itemIndex)) {
                            itemHave[itemIndex] = true; // 상태 변경
                            itemMounting[itemIndex] = true;
                            editUserData.putBoolean("item"+itemIndex+"mounting", itemMounting[itemIndex]);
                            editUserData.putBoolean("item"+itemIndex, itemHave[itemIndex]);
                            editUserData.putInt("money", money);
                            editUserData.commit();
                            viewMoney.setText(String.valueOf(money));
                            updateButtonLabel(button, itemIndex); // 버튼 레이블 업데이트
                        } else {
                            Toast.makeText(getApplicationContext(), "구매에 실패했습니다. 잔액이 부족합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            // 아이템 레이아웃을 컨테이너에 추가
            container.addView(itemLayout);
        }
    }

    private void updateButtonLabel(Button button, int itemIndex) {
        if (itemHave[itemIndex]) {
            if (itemMounting[itemIndex]) {
                button.setText("해제");
                button.setBackgroundColor(Color.LTGRAY);
            } else {
                button.setText("장착");
                button.setBackgroundColor(Color.rgb(255, 153, 153));
            }
        } else {
            button.setText("구입");
            button.setBackgroundColor(Color.GRAY);
        }
    }

    private boolean purchaseItem(int itemIndex) {
        if (itemPrices[itemIndex] > money) {
            return false;
        } else {
            money -= itemPrices[itemIndex];
            return true;
        }
    }
}