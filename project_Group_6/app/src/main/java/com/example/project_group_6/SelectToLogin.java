package com.example.project_group_6;
import  com.example.project_group_6.Administrator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;



public class SelectToLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnCook;
    private Button btnClient;
    private TextView title;
    private Button btnAdmin;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        initView();

        addAdministrator_account();

    }

    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        btnClient = findViewById(R.id.btnclient);
        btnCook = findViewById(R.id.btncook);
        btnAdmin = findViewById(R.id.btnadmin);

        btnCook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SelectToLogin.this, LoginActivityCook.class);
                startActivity(intent);
                finish();
            }
        });

        btnClient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SelectToLogin.this, LoginActivityClient.class);
                startActivity(intent);
                finish();
            }
        });
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SelectToLogin.this, LoginActivityAdmin.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void addAdministrator_account() {

        Administrator administrator = new Administrator("Kevin", "Wu", "kevin_wu", "wuwuwu123");


        mAuth.createUserWithEmailAndPassword(administrator.get_email_address(), administrator.get_account_password())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference("Administrator")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(administrator);
                        }
                    }

                });
    }
}