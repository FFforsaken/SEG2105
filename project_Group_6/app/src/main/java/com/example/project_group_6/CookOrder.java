package com.example.project_group_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CookOrder extends AppCompatActivity implements OrderAdaptor.ItemClickListener{
    String cookID;
    DatabaseReference databaseReference;
    ArrayList<String> orders;
    ArrayList<String> types;
    ArrayList<String> clients;
    ArrayList<String> allIds;
    RecyclerView recyclerView;
    OrderAdaptor adapter;
    LinearLayoutManager layoutManager;

    public static class Order {

        public String client;
        public String cook;
        public String meal;
        public String type;
        public Boolean complete;
        public Boolean accept;
        public Double rating;
        public Boolean decline;
        public Order(){}

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_order);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-7629811168995371601-default-rtdb.firebaseio.com/");


        SharedPreferences prefs = getSharedPreferences("UserID", MODE_PRIVATE);
        cookID = prefs.getString("CookID", "");
        orders = new ArrayList<String>();
        clients = new ArrayList<String>();
        allIds = new ArrayList<String>();
        types = new ArrayList<String>();


        adapter = new OrderAdaptor(orders,clients);
        adapter.setClickListener(this);

        recyclerView = findViewById(R.id.ListofOrders);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Log.d("DB out","hahaha");
        databaseReference.child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orders.clear();
                clients.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Order curOrder = child.getValue(Order.class);
                    if(cookID.equals(curOrder.cook) && !curOrder.accept&&!curOrder.decline){
                        Log.d("DB out",curOrder.meal);
                        orders.add(curOrder.meal);
                        types.add(curOrder.type);
                        clients.add(curOrder.client);
                        allIds.add(child.getKey());
                        Log.d("DB out",(curOrder.accept).toString());

                    }
//                    Log.d("DB out",curOrder.client);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB expection", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        String id = allIds.get(position);
        String meal = orders.get(position);
        String type = types.get(position);
        Log.d("click","data click");
        Log.d("click","data click" + view.getId());
        if(view.getId() == R.id.order_accept) {
            CookMenu.Menu cur = new CookMenu.Menu();
//            cur.name = meal;
//            cur.type = type;
//            databaseReference.child("Menu").child(cookID).child(id).setValue(cur);
            databaseReference.child("Orders").child(id).child("accept").setValue(true);
            Log.d("click","data click");
        }
        else{
            if(view.getId() == R.id.order_decline) {
                databaseReference.child("Orders").child(id).child("decline").setValue(true);
            }

        }
//        if(view.getId() == R.id.menu_text) {
//            String food = adapter.getItem(position);
//            // Add
//            databaseReference.child("Menu").child(cookID).child(String.valueOf(id+1)).setValue(food);
//            id += 1;
//            Toast.makeText(this, "Added " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MenuList.this, CookMain.class));
//        }
//        else{
//            String food = adapter.getItem(position);
//            if(!listOfMenu.contains(food)){
//                databaseReference.child("MenuList").child(cookID).child(food).removeValue();
//            }
//            else{
//                Toast.makeText(this, "Unable to remove " + food + "as it is in current offer", Toast.LENGTH_SHORT).show();
//            }
//        databaseReference.child("MenuList").child(cookID).removeValue();
//        ComplaintPop popUpClass = new ComplaintPop();
//        popUpClass.showPopupWindow(view,listOfComplaints.get(position),listOfCooks.get(position),listOfClient.get(position),databaseReference);

    }





}