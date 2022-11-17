package com.example.project_group_6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuList extends AppCompatActivity implements MenuListAdaptor.ItemClickListener {
    String cookID;
    DatabaseReference databaseReference;
    ArrayList<String> menuList;
    RecyclerView recyclerView;
    MenuListAdaptor adapter;
    LinearLayoutManager layoutManager;
    ArrayList<String> listOfMenu;
    boolean suspend;


    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menulist);
        SharedPreferences prefs = getSharedPreferences("UserID", MODE_PRIVATE);
        cookID = prefs.getString("CookID", "");
        menuList = new ArrayList<String>();
        listOfMenu = new ArrayList<String>();


        adapter = new MenuListAdaptor(menuList);
        adapter.setClickListener(this);

        recyclerView = findViewById(R.id.menulist);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-7629811168995371601-default-rtdb.firebaseio.com/");

        databaseReference.child("cook_accounts").child(cookID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suspend = dataSnapshot.child("_suspend").getValue(boolean.class);
                refreshMenu();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB expection", "Failed to read value.", error.toException());
            }
        });
    }

    private void filterMenuList(){
        databaseReference.child("Menu").child(cookID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listOfMenu.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    listOfMenu.add(child.getKey());
                    Log.d("DB out",child.getKey());
                }
                generateMenuList();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB expection", "Failed to read value.", error.toException());
            }
        });

    }
    private void generateMenuList(){
        databaseReference.child("MenuList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuList.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String food = child.getKey();
                    if(!listOfMenu.contains(food)){
                        menuList.add(child.getKey());
                    }
                    Log.d("DB out",child.getKey());
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
        String food = adapter.getItem(position);
       // Add
        databaseReference.child("Menu").child(cookID).child(food).setValue("");
        Toast.makeText(this, "Added " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MenuList.this,CookMenu.class));
//        databaseReference.child("MenuList").child(cookID).removeValue();
//        ComplaintPop popUpClass = new ComplaintPop();
//        popUpClass.showPopupWindow(view,listOfComplaints.get(position),listOfCooks.get(position),listOfClient.get(position),databaseReference);

    }

    public void refreshMenu() {
        adapter.setEnable(!suspend);
        if (suspend) {
            menuList.clear();
            menuList.add("You are suspended");
            adapter.notifyDataSetChanged();
            return;
        }
        filterMenuList();
    }
}
