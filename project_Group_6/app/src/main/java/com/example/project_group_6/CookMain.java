package com.example.project_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CookMain extends AppCompatActivity {

    Button btn_logout_cook;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_main);
        initView();
    }

    public void initView(){
        btn_logout_cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CookMain.this, SelectToLogin.class);
                startActivity(intent);
            }
        });
    }

}
