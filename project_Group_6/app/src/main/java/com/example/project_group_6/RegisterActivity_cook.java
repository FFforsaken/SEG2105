package com.example.project_group_6;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
public class RegisterActivity_cook extends AppCompatActivity  {
    private Button btnRegister;
    private Button btnCancel;
    private EditText etAccount;
    private EditText etPassword;
    private EditText etFirstname;
    private EditText etLastname;
    private  EditText etCheque;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cook);
        initView();
    }
    public void initView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        // 绑定控件
        etAccount =findViewById(R.id.et_account_cook);
        etPassword = findViewById(R.id.et_password_cook);
        etCheque = findViewById(R.id.et_cheque);
        etFirstname = findViewById(R.id.et_firstname_cook);
        etLastname = findViewById(R.id.et_lastname_cook);
        btnRegister = findViewById(R.id.btn_register_cook);
        btnCancel = findViewById(R.id.btn_cancel_cook);

        // 设置点击事件

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
