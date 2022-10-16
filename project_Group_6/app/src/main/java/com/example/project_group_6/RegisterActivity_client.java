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
public class RegisterActivity_client extends AppCompatActivity {

    private Button btnRegister;
    private Button btnCancel;
    private EditText etAccount;
    private EditText etPassword;
    private EditText etFirstname;
    private EditText etLastname;
    private  EditText etCredit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        initView();
    }
    public void initView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        // 绑定控件
        etAccount =findViewById(R.id.et_account_client);
        etPassword = findViewById(R.id.et_password_client);
        etCredit = findViewById(R.id.et_creditinfo);
        etFirstname = findViewById(R.id.et_firstname_client);
        etLastname = findViewById(R.id.et_lastname_client);
        btnRegister = findViewById(R.id.btn_register_client);
        btnCancel = findViewById(R.id.btn_cancel_client);

        // 设置点击事件

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
