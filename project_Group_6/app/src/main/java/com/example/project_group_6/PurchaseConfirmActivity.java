package com.example.project_group_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.java_classes.Request;
import com.example.java_classes.Status;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PurchaseConfirmActivity extends AppCompatActivity {
    TextView mealName;
    MenuAdaptor meal;
    private DatabaseReference requestReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_confirm);

        Intent intent =  getIntent();


        requestReference = FirebaseDatabase.getInstance().getReference("request");
    }

    public void onPurchaseBtnClick(View view){
        requestReference.push().setValue(new Request(meal, Status.PENDING));
        finish();
    }
}