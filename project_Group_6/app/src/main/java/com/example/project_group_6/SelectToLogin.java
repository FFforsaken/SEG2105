package com.example.project_group_6;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
public class SelectToLogin extends AppCompatActivity {


    private Button btnCook;
    private Button btnClient;
    private TextView title;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_direction);
        initView();
    }

    public void initView(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        btnClient =findViewById(R.id.btnclient);
        btnCook = findViewById(R.id.btncook);


        btnCook.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(SelectToLogin.this, LoginActivityCook.class);
                startActivity(intent);
                finish();
            }
        });

        btnClient.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(SelectToLogin.this, LoginActivityClient.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
