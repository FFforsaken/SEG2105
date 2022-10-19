package com.example.project_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivityAdmin extends AppCompatActivity {
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;



    DatabaseReference database_Administrator_account;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        initView();
        database_Administrator_account = FirebaseDatabase.getInstance().getReference("Administrator_accounts");



    }
    public void initView(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();


        btnLogin = findViewById(R.id.btn_login_admin);
        etUsername = findViewById(R.id.et_account_admin);
        etPassword = findViewById(R.id.et_password_admin);


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                is_Admin();
            }
        });



    }

    private boolean validate_username() {
        String val = etUsername.getText().toString().trim();

        if (val.isEmpty()) {
            etUsername.setError("Username cannot be empty");
            return false;
        }

        else{
            etUsername.setError(null);
            return true;
        }


    }

    private boolean validate_password() {
        String val = etPassword.getText().toString().trim();

        if (val.isEmpty()) {
            etPassword.setError("Password cannot be empty");
            return false;
        }

        else{
            etPassword.setError(null);
            return true;
        }


    }

    public void Login_Admin(){
        if(!validate_username() || !validate_password()){
            return;
        }

        else{
            is_Admin();
        }




    }

    private void is_Admin() {

        String UserEntered_username = etUsername.getText().toString().trim();
        String UserEntered_password = etPassword.getText().toString().trim();

        Query checkAdmin = database_Administrator_account.orderByChild("_user_name").equalTo(UserEntered_username);

        checkAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    etUsername.setError(null);


                    String passwordFromDB = dataSnapshot.child("_user_name").child("_account_password").getValue(String.class);

                    if (passwordFromDB.equals(UserEntered_password)){
                        etPassword.setError(null);
                        Intent intent = new Intent(LoginActivityAdmin.this, administrator_main_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{

                        etPassword.setError("Password is wrong!");
                        etPassword.requestFocus();
                    }
                }
                else{
                    etUsername.setError("No such User exists");
                    etUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}
