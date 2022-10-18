package com.example.project_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
public class LoginActivityAdmin extends AppCompatActivity {
    private Button btnLogin;
    private EditText etAccount;
    private EditText etPassword;
    private TextView tvRegister;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        initView();
    }
    public void initView(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();


        btnLogin = findViewById(R.id.btn_login_admin);
        etAccount = findViewById(R.id.et_account_admin);
        etPassword = findViewById(R.id.et_password_admin);


    }
}
