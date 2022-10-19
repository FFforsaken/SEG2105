package com.example.project_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivityClient extends AppCompatActivity {

    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;


    DatabaseReference databaseReference;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);
        initView();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-group-6-c1e1f-default-rtdb.firebaseio.com/");
    }
    public void initView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();


        btnLogin = findViewById(R.id.btn_login_client);

        etUsername = findViewById(R.id.et_user_name_client);
        etPassword = findViewById(R.id.et_password_client);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Login_Admin();
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

    public void Login_Client(){
        if(!validate_username() || !validate_password()){
            return;
        }
        else{
            is_Client();
        }

    }
    
    private void is_Client() {

        String UserEntered_username = etUsername.getText().toString().trim();
        String UserEntered_password = etPassword.getText().toString().trim();



        databaseReference.child("client_accounts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(UserEntered_username)){

                    etUsername.setError(null);


                    final String getPasswordfromDB = dataSnapshot.child(UserEntered_username).child("_account_password").getValue(String.class);

                    if (getPasswordfromDB.equals(UserEntered_password)){
                        etPassword.setError(null);
                        Intent intent = new Intent(LoginActivityClient.this, WelcomeforClient.class);
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
