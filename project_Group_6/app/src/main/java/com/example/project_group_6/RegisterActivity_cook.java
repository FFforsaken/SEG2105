package com.example.project_group_6;
import com.example.project_group_6.Cook;
import android.content.Intent;
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
    private Boolean result;

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
        result = true;
        // 设置点击事件

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity_cook.this, LoginActivityCook.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener(){
            public void  onClick(View v){
                addcook_account();
                if(result==true){
                Intent intent = new Intent(RegisterActivity_cook.this, LoginActivityCook.class);
                startActivity(intent);
                finish();
            }}
        });
    }

    public void addcook_account() {

        String Firstname = etFirstname.getText().toString().trim();
        String Lastname = etLastname.getText().toString().trim();
        String Username = etUsername.getText().toString().trim().replace(".","");
        String Password = etPassword.getText().toString().trim();
        String address_cook = et_address_cook.getText().toString().trim();
        String shortdiscription = et_shortdiscription.getText().toString().trim();

        etFirstname.setError(null);
        etLastname.setError(null);
        etUsername.setError(null);
        etPassword.setError(null);
        et_address_cook.setError(null);
        et_shortdiscription.setError(null);
        ///////////////////////////////////////////////////////////////////////////////////////////
        if(Firstname.isEmpty()){
            etFirstname.setError("CANT BE EMPTY!");
            etFirstname.requestFocus();
            result = false;
            return;
        }
        if(Lastname.isEmpty()){
            etLastname.setError("CANT BE EMPTY!");
            etLastname.requestFocus();
            result = false;
            return;
        }
        if(Username.isEmpty()){
            etUsername.setError("CANT BE EMPTY!");
            etUsername.requestFocus();
            result = false;
            return;
        }
        if(Password.isEmpty()){
            etPassword.setError("CANT BE EMPTY!");
            etPassword.requestFocus();
            result = false;
            return;
        }
        if(address_cook.isEmpty()){
            et_address_cook.setError("CANT BE EMPTY!");
            et_address_cook.requestFocus();
            result = false;
            return;
        }
        if(shortdiscription.isEmpty()){
            et_shortdiscription.setError("CANT BE EMPTY!");
            et_shortdiscription.requestFocus();
            result = false;
            return;
        }


        // String id = database_cook_account.push().getKey();
        Cook cook = new Cook(Firstname,Lastname,Username,Password,address_cook);

        String username = cook.get_user_name();

        database_cook_account.child(username).setValue(cook);

        etFirstname.setText("");
        etLastname.setText("");
        etUsername.setText("");
        etPassword.setText("");
        et_address_cook.setText("");
        et_shortdiscription.setText("");

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
