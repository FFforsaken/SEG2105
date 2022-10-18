package com.example.project_group_6;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

    DatabaseReference database_client_account;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        initView();
        database_client_account = FirebaseDatabase.getInstance().getReference("client_account");
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

        // 设置点击事件
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener(){
            public void  onClick(View v){
                addClient_account();
                finish();
            }
        });
    }

    //////////////// method to add, update and delete an account from database;
    public void addClient_account(){

        String Firstname = etFirstname.getText().toString().trim();
        String Lastname = etLastname.getText().toString().trim();
        String Username = etUsername.getText().toString().trim();
        String Password = etPassword.getText().toString().trim();
        Long credit_card_number = Long.parseLong(String.valueOf(et_credit_card_number.getText().toString()));
        String credit_card_expiration_date = et_credit_card_expiration_date.getText().toString().trim();
        int CVV = Integer.parseInt(String.valueOf(etCVV.getText().toString()));
        String address_client = et_address_client.getText().toString().trim();




        // Create a Product object and save this object
//      String id = database_client_account.push().getKey();

        Client client = new Client(Firstname,Lastname,Username,Password,address_client,credit_card_number,credit_card_expiration_date,CVV);
        String username = client.get_user_name();

        database_client_account.child(username).setValue(client);


        // clear the text boxes
        etFirstname.setText("");
        etLastname.setText("");
        etUsername.setText("");
        etPassword.setText("");
        et_credit_card_number.setText("");
        et_credit_card_expiration_date.setText("");
        etCVV.setText("");
        et_address_client.setText("");

        Toast.makeText(this, "Client Account created successfully",Toast.LENGTH_LONG).show();
//
//        }
//        else{
//            Toast.makeText(this, "Please enter a name",Toast.LENGTH_LONG).show();
//        }
    }

    private void updateClient_account_info(String first_name, String last_name, String email_address, String account_password, String address, long credit_card_number, String credit_card_expiration_date, int CVV) {



        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("client_account").child(email_address);

        // update the product by using setValue()
        Client client = new Client(first_name,last_name,email_address,account_password,address,credit_card_number,credit_card_expiration_date,CVV);
        dR.setValue(client);

        Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();


    }

    private boolean deleteClient_account(String username) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("client_account").child(username);

        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Account Deleted successfully", Toast.LENGTH_LONG).show();

        return true;
    }



    private void clickOnRegister() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClient_account();
            }
        });
    }

}
