package com.example.project_group_6;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.widget.Toast;
public class RegisterActivity_cook extends AppCompatActivity  {
    private Button btnRegister;
    private Button btnCancel;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etFirstname;
    private EditText etLastname;
    private EditText et_address_cook;
    private EditText et_shortdiscription;


    DatabaseReference database_cook_account;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cook);
        initView();
        database_cook_account = FirebaseDatabase.getInstance().getReference("cook_accounts");
    }
    public void initView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        // 绑定控件
        etUsername = findViewById(R.id.et_username_cook);
        etPassword = findViewById(R.id.et_password_cook);
        etFirstname = findViewById(R.id.et_firstname_cook);
        etLastname = findViewById(R.id.et_lastname_cook);
        et_address_cook = findViewById(R.id.et_address_cook);
        et_shortdiscription = findViewById(R.id.et_shortdiscription);

        btnRegister = findViewById(R.id.btn_register_cook);
        btnCancel = findViewById(R.id.btn_cancel_cook);

        // 设置点击事件

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener(){
            public void  onClick(View v){
                addcook_account();
                finish();
            }
        });
    }

    public void addcook_account() {

        String Firstname = etFirstname.getText().toString().trim();
        String Lastname = etLastname.getText().toString().trim();
        String Username = etUsername.getText().toString().trim();
        String Password = etPassword.getText().toString().trim();
        String address_cook = et_address_cook.getText().toString().trim();

        String id = database_cook_account.push().getKey();
        Cook cook =new Cook(Firstname,Lastname,Username,Password,address_cook);

//        String username = cook.get_user_name();

        database_cook_account.child(id).setValue(cook);

        etFirstname.setText("");
        etLastname.setText("");
        etUsername.setText("");
        etPassword.setText("");
        et_address_cook.setText("");

        Toast.makeText(this, "Cook Account created successfully",Toast.LENGTH_LONG).show();


    }

//    private void updateCook_account_info(String first_name, String last_name, String email_address, String account_password, String address) {
//
//
//
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("cook_account").child(email_address);
//
//        // update the product by using setValue()
//        Cook cook = new Cook(first_name,last_name,email_address,account_password,address);
//        dR.setValue(cook);
//
//        Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();
//
//
//    }
//
//    private boolean deletecook_account(String username) {
//
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("cook_account").child(username);
//
//        dR.removeValue();
//
//        Toast.makeText(getApplicationContext(), "Account Deleted successfully", Toast.LENGTH_LONG).show();
//
//        return true;
//    }










}
