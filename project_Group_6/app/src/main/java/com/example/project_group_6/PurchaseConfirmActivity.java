package com.example.project_group_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.java_classes.Request;
import com.example.java_classes.Status;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PurchaseConfirmActivity extends AppCompatActivity {
    TextView mealName;
    private DatabaseReference databaseReference;
    String meal,type,cook,client;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_confirm);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-7629811168995371601-default-rtdb.firebaseio.com/");


        Intent intent =  getIntent();
        Bundle bundle = getIntent().getExtras();


        SharedPreferences prefs = getSharedPreferences("UserID", MODE_PRIVATE);
        client = prefs.getString("ClientID", "");
        if(bundle.getString("meal")!= null)
        {
            Log.d( "Comfirm", bundle.getString("meal"));
            String[] arrOfStr = bundle.getString("meal").split(",");
            meal = arrOfStr[0].trim();
            type = arrOfStr[1].split(":")[1].trim();
            cook = arrOfStr[2].split(":")[1].trim();
            Log.d( "Comfirm", meal + type+cook);
        }


        databaseReference.child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               id = 0;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    CookMenu.Menu cur = child.getValue(CookMenu.Menu.class);
                    if(Integer.parseInt(child.getKey()) > id){
                        id = Integer.parseInt(child.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB expection", "Failed to read value.", error.toException());
            }
        });
    }

    public void onPurchaseBtnClick(View view){
        CookOrder.Order  cur = new CookOrder.Order();
        cur.client = client;
        cur.cook = cook;
        cur.meal = meal;
        cur.type = type;
        cur.complete = false;
        cur.accept = false;
        cur.decline = false;
        cur.rating = 0.0;
        databaseReference.child("Orders").child(String.valueOf(id+1)).setValue(cur);
//        requestReference.push().setValue(new Request(meal, Status.PENDING));
        finish();
    }
}