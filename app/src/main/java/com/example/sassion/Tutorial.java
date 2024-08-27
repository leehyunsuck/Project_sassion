package com.example.sassion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Tutorial extends AppCompatActivity {
    private boolean check = false;
    private boolean select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

//        Button  selectGreen = findViewById(R.id.selectGreen),
//                selectPink = findViewById(R.id.selectPink);

        ImageView   viewSelect = findViewById(R.id.tutorialSelect),
                    view1 = findViewById(R.id.tutorial1),
                    view2 = findViewById(R.id.tutorial2),
                    view3 = findViewById(R.id.tutorial3),
                    view4 = findViewById(R.id.tutorial4),
                    view5 = findViewById(R.id.tutorial5);

        view1.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);
        view4.setVisibility(View.INVISIBLE);
        view5.setVisibility(View.INVISIBLE);

        viewSelect.setVisibility(View.INVISIBLE);
        view1.setVisibility(View.VISIBLE);
        check = true;


        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                if (check) {
                    if (id == view1.getId()) {
                        view1.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.VISIBLE);
                    } else if (id == view2.getId()) {
                        view2.setVisibility(View.INVISIBLE);
                        view3.setVisibility(View.VISIBLE);
                    } else if (id == view3.getId()) {
                        view3.setVisibility(View.INVISIBLE);
                        view4.setVisibility(View.VISIBLE);
                    } else if (id == view4.getId()) {
                        view4.setVisibility(View.INVISIBLE);
                        view5.setVisibility(View.VISIBLE);
                    } else if (id == view5.getId()) {
                        SharedPreferences userData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editUserData = userData.edit();
                        editUserData.putBoolean("choiceCharacter", select);
                        editUserData.commit();
                        Intent intent = new Intent(Tutorial.this, HomeActivity.class);
                        startActivity(intent);
                    }
                } else {
//                    if (id == selectGreen.getId()) {
//                        select = false;
//                    } else if (id == selectPink.getId()) {
//                        select = true;
//                    }
//                    selectGreen.setVisibility(View.INVISIBLE);
//                    selectPink.setVisibility(View.INVISIBLE);
//                    viewSelect.setVisibility(View.INVISIBLE);
//                    view1.setVisibility(View.VISIBLE);
//                    check = true;
                }
            }
        };
//        selectGreen.setOnClickListener(click);
//        selectPink.setOnClickListener(click);
        view1.setOnClickListener(click);
        view2.setOnClickListener(click);
        view3.setOnClickListener(click);
        view4.setOnClickListener(click);
        view5.setOnClickListener(click);
    }
}