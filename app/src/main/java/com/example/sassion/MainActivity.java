package com.example.sassion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent moveLoginPage = new Intent(this, Login.class);
        moveLoginPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent moveHomePage = new Intent(this, HomeActivity.class);
        moveHomePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        Button login = findViewById(R.id.registerButton);
        Button remove = findViewById(R.id.removeData);
        Button change = findViewById(R.id.change);
        Button shop = findViewById(R.id.buttonShop);
        Button tuto = findViewById(R.id.tuto);
        Button detail1 = findViewById(R.id.detailButton1);
        Button detail2 = findViewById(R.id.detailButton2);
        Button detail3 = findViewById(R.id.detailButton3);
        Button write = findViewById(R.id.writeChall);


        TextView join = findViewById(R.id.test);

        //정보 받기
        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editUserData = userData.edit();

        String userEmail = userData.getString("email", "");

        if (userEmail.isEmpty()) {
            startActivity(moveLoginPage);
        } else {
            startActivity(moveHomePage);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                if (id == login.getId()) {
                    Intent loginpage = new Intent(MainActivity.this, Login.class);
                    startActivity(loginpage);
                } else if (id == remove.getId()) {
                    editUserData.clear();
                    if (editUserData.commit()) {
                        join.setText(userData.getString("email", ""));
                    }
                } else if (id == change.getId()) {
                    Intent changePage = new Intent(MainActivity.this, ChangeInfo.class);
                    startActivity(changePage);
                } else if (id == shop.getId()) {
                    Intent shopPage = new Intent(MainActivity.this, Shop.class);
                    startActivity(shopPage);
                } else if (id == tuto.getId()) {
                    Intent tutoPage = new Intent(MainActivity.this, Tutorial.class);
                    startActivity(tutoPage);
                } else if (id == detail1.getId()) {
                    Intent detailPage = new Intent(MainActivity.this, DetailActivity.class);
                    detailPage.putExtra("selectDay", 1);
                    startActivity(detailPage);
                } else if (id == detail2.getId()) {
                    Intent detailPage = new Intent(MainActivity.this, DetailActivity.class);
                    detailPage.putExtra("selectDay", 2);
                    startActivity(detailPage);
                } else if (id == detail3.getId()) {
                    Intent detailPage = new Intent(MainActivity.this, DetailActivity.class);
                    detailPage.putExtra("selectDay", 3);
                    startActivity(detailPage);
                } else if (id == write.getId()) {
                    Intent writePage = new Intent(MainActivity.this, WriteActivity.class);
                    startActivity(writePage);
                }
            }
        };
        login.setOnClickListener(clickListener);
        remove.setOnClickListener(clickListener);
        change.setOnClickListener(clickListener);
        shop.setOnClickListener(clickListener);
        tuto.setOnClickListener(clickListener);
        detail1.setOnClickListener(clickListener);
        detail2.setOnClickListener(clickListener);
        detail3.setOnClickListener(clickListener);
        write.setOnClickListener(clickListener);
    }
}
