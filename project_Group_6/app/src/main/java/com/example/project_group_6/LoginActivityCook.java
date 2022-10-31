package com.example.project_group_6;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivityCook extends AppCompatActivity {
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvRegist;
//    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cook);
        mAuth = FirebaseAuth.getInstance();
        initView();
//        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-group-6-c1e1f-default-rtdb.firebaseio.com/");
    }
    
    public void initView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();


        btnLogin = findViewById(R.id.btn_login);
         etUsername = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        tvRegist = findViewById(R.id.tv_register);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_Cook();
            }
        });
        tvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivityCook.this, RegisterActivity_cook.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean validate_username() {
        String val = etUsername.getText().toString().trim();

        if (val.isEmpty()) {
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            etUsername.setError("Please enter a valid email!");
            etUsername.requestFocus();
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
            etUsername.requestFocus();
            return false;
        }

        else{
            etPassword.setError(null);
            return true;
        }

    }

    public void Login_Cook(){
        if(!validate_username() || !validate_password()){
            return;
        }
        else{
            is_Cook();
        }

    }

    private void is_Cook() {

        String UserEntered_username = etUsername.getText().toString().trim().replace(".","");
        String UserEntered_password = etPassword.getText().toString().trim();


        mAuth.signInWithEmailAndPassword(UserEntered_username, UserEntered_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivityCook.this, WelcomeforCook.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivityCook.this, "Failed to login! Please check your credentials.",Toast.LENGTH_LONG).show();
                }
            }

        });



    }

}
