package com.example.project_group_6;
import com.example.project_group_6.Client;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


import android.widget.Toast;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class RegisterActivity_client extends AppCompatActivity {

    private Button btnRegister;
    private Button btnCancel;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etFirstname;
    private EditText etLastname;
    private  EditText etCredit;
    private  EditText et_credit_card_number;
    private  EditText et_credit_card_expiration_date;
    private  EditText etCVV;
    private EditText  et_address_client;
    private boolean result;
    private FirebaseAuth mAuth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        mAuth = FirebaseAuth.getInstance();
        initView();


    }
    public void initView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        // 绑定控件
        etPassword = findViewById(R.id.et_password_client);
        etFirstname = findViewById(R.id.et_firstname_client);
        etLastname = findViewById(R.id.et_lastname_client);
        etUsername =findViewById(R.id.et_user_name_client);
        et_credit_card_number = findViewById(R.id.et_credit_card_number);
        et_credit_card_expiration_date = findViewById(R.id.et_credit_card_expiration_date);
        etCVV = findViewById(R.id.et_CVV);
        et_address_client = findViewById(R.id.et_address_client);
        btnRegister = findViewById(R.id.btn_register_client);
        btnCancel = findViewById(R.id.btn_cancel_client);
        result = true;

        // 设置点击事件
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity_client.this, LoginActivityClient.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener(){
            public void  onClick(View v){
                addClient_account();

                if(result == true){
                    Intent intent = new Intent(RegisterActivity_client.this, LoginActivityClient.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    //////////////// method to add user to database;
    public void addClient_account(){

        String Firstname = etFirstname.getText().toString().trim();
        String Lastname = etLastname.getText().toString().trim();
        String Username = etUsername.getText().toString().trim();
        String Password = etPassword.getText().toString().trim();
        String credit_card_number = et_credit_card_number.getText().toString().trim();
        String credit_card_expiration_date = et_credit_card_expiration_date.getText().toString().trim();
        String CVV = etCVV.getText().toString().trim();
        String address_client = et_address_client.getText().toString().trim();

        etFirstname.setError(null);
        etLastname.setError(null);
        etUsername.setError(null);
        etPassword.setError(null);
        et_credit_card_number.setError(null);
        et_credit_card_expiration_date.setError(null);
        et_address_client.setError(null);
        etCVV.setError(null);

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
        if(credit_card_number.isEmpty() ||  credit_card_number.length() != 16){
            et_credit_card_number.setError("Invalid Input!");
            et_credit_card_number.requestFocus();
            result = false;
            return;
        }

        if(credit_card_expiration_date.isEmpty() || (credit_card_expiration_date.length() != 5 && credit_card_expiration_date.contains("/"))){
            et_credit_card_expiration_date.setError("Invalid Input!");
            et_credit_card_expiration_date.requestFocus();
            result = false;
            return;
        }
        if(CVV.isEmpty() || CVV.length() != 3){
            etCVV.setError("Invalid Input!");
            etCVV.requestFocus();
            result = false;
            return;
        }

        if(address_client.isEmpty()){
            et_address_client.setError("CANT BE EMPTY!");
            et_address_client.requestFocus();
            result = false;
            return;
        }

        // Create a Product object and save this object
        Client client = new Client(Firstname, Lastname, Username, Password, address_client, credit_card_number, credit_card_expiration_date, CVV);

        mAuth.createUserWithEmailAndPassword(client.get_email_address(), client.get_account_password())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference("clients")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(client).addOnCompleteListener(new OnCompleteListener<Void>(){
                                @Override
                                public void onComplete(@NonNull Task<Void> task){

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity_client.this, "Client Account created successfully",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity_client.this, "Failed to register! something went wrong.",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }

                });

        // clear the text boxes
        etFirstname.setText("");
        etLastname.setText("");
        etUsername.setText("");
        etPassword.setText("");
        et_credit_card_number.setText("");
        et_credit_card_expiration_date.setText("");
        etCVV.setText("");
        et_address_client.setText("");


    }
}
