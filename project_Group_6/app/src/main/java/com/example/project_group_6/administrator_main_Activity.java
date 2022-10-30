package com.example.project_group_6;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class administrator_main_Activity extends AppCompatActivity {
    Button btn_logout_admin;
    Button btn_complaints;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_main);
        initView();
    }

    public void initView(){
        btn_logout_admin = findViewById(R.id.btn_logout_admin);
        btn_logout_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(administrator_main_Activity.this, SelectToLogin.class);
                startActivity(intent);
                finish();
            }
        });


        btn_complaints = findViewById(R.id.btn_complaints);

        btn_complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(administrator_main_Activity.this, list_of_complaints.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
