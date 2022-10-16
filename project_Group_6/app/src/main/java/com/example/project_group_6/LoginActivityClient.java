package com.example.project_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivityClient extends AppCompatActivity {

    private Button btnLogin;
    private EditText etAccount;
    private EditText etPassword;
    private TextView tvRegister;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);
        initView();
    }
    public void initView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();


        btnLogin = findViewById(R.id.btn_login_client);
        tvRegister = findViewById(R.id.tv_register_client);
        etAccount = findViewById(R.id.et_account_client);
        etPassword = findViewById(R.id.et_password_client);



        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivityClient.this, RegisterActivity_client.class);
                startActivityForResult(intent,1);
            }
        });
    }

}
