package com.example.project_group_6;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class SelectToLogin extends AppCompatActivity {


    private Button btnCook;
    private Button btnClient;
    private TextView title;
    private Button btnAdmin;
    DatabaseReference database_default_administrator_account;
    private int time = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database_default_administrator_account = FirebaseDatabase.getInstance().getReference("Administrator_accounts");
        initView();

        if (time == 0){
            addAdministrator_account();
            time++;
        }

    }

    public void initView(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        btnClient =findViewById(R.id.btnclient);
        btnCook = findViewById(R.id.btncook);
        btnAdmin = findViewById(R.id.btnadmin);

        btnCook.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(SelectToLogin.this, LoginActivityCook.class);
                startActivity(intent);
                finish();
            }
        });

        btnClient.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(SelectToLogin.this, LoginActivityClient.class);
                startActivity(intent);
                finish();
            }
        });
        btnAdmin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(SelectToLogin.this, LoginActivityAdmin.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void addAdministrator_account(){
        String id =  database_default_administrator_account.push().getKey();
        Administrator administrator = new Administrator("Kevin","Wu","kevin_wu","wuwuwu123");

//        String username = administrator.get_user_name();

        database_default_administrator_account.child(id).setValue(administrator);

    }

}
