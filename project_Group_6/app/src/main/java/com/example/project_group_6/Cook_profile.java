package com.example.project_group_6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cook_profile extends AppCompatActivity {

    String cookID;
    DatabaseReference databaseReference;
    TextView rating,last,first,email,username,password,address;
    Button update;

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_profile);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-7629811168995371601-default-rtdb.firebaseio.com/");


        SharedPreferences prefs = getSharedPreferences("UserID", MODE_PRIVATE);
        cookID = prefs.getString("CookID", "");
        rating = findViewById(R.id.text_rating);
        last = findViewById(R.id.et_lastname_profile);
        first = findViewById(R.id.et_firstname_profile);
        email = findViewById(R.id.et_username_profile);
        password = findViewById(R.id.et_password_profile);
        address = findViewById(R.id.et_address_profile);
        username = findViewById(R.id.text_name);
        update = findViewById(R.id.btn_update_p);

        databaseReference.child("cook_accounts").child(cookID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    switch(child.getKey()) {
                        case "_last_name":
                            last.setText(child.getValue(String.class));
                            // code block
                            break;
                        case "_first_name":
                            first.setText(child.getValue(String.class));
                            // code block
                            break;
                        case "_account_password":
                            password.setText(child.getValue(String.class));
                            // code block
                            break;
                        case "_address":
                            address.setText(child.getValue(String.class));
                            // code block
                            break;
                        case "_user_name":
                           username.setText(child.getValue(String.class));
                            // code block
                            break;
                        case "_rating":
                            rating.setText("Your current rating: " + String.valueOf(child.getValue(double.class)));
                            // code block
                            break;
                        case "_email_address":
                            email.setText(child.getValue(String.class));
                            // code block
                            break;
                        default:
                            // code block
                    }
//                    name.setText((String)first.getText()+(String)last.getText());
//                    Log.d("DB out",curOrder.client);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB expection", "Failed to read value.", error.toException());
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                databaseReference.child("cook_accounts").child(cookID).child("_last_name").setValue(last.getText().toString());
                databaseReference.child("cook_accounts").child(cookID).child("_first_name").setValue(first.getText().toString());
                databaseReference.child("cook_accounts").child(cookID).child("_account_password").setValue(password.getText().toString());
                databaseReference.child("cook_accounts").child(cookID).child("_address").setValue(address.getText().toString());
                databaseReference.child("cook_accounts").child(cookID).child("_email_address").setValue(email.getText().toString());
                Toast.makeText(getApplicationContext(),"Update succeed",Toast. LENGTH_SHORT).show();
            }
        });

        databaseReference.child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double rating = 0;
                int num = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    CookOrder.Order curOrder = child.getValue(CookOrder.Order.class);
                    if(curOrder.complete && curOrder.cook.equals(cookID)){
                        rating += curOrder.rating;
                        num += 1;
                    }
                }
                if(num != 0){
                    rating = rating/num;
                }
                databaseReference.child("cook_accounts").child(cookID).child("_rating").setValue(rating);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB expection", "Failed to read value.", error.toException());
            }
        });
        }




}
