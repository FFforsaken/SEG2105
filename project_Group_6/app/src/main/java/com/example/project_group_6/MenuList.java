package com.example.project_group_6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuList extends AppCompatActivity implements MenuAdaptor.ItemClickListener{
    String cookID;
    DatabaseReference databaseReference;
    ArrayList<String> menuList;
    ArrayList<String> typeList;
    ArrayList<String> idList;
    RecyclerView recyclerView;
    MenuAdaptor adapter;
    LinearLayoutManager layoutManager;
    ArrayList<String> listOfMenu;
    int id;
    int menuId;
    boolean suspend;


    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        SharedPreferences prefs = getSharedPreferences("UserID", MODE_PRIVATE);
        cookID = prefs.getString("CookID", "");
        menuList = new ArrayList<String>();
        listOfMenu = new ArrayList<String>();
        typeList = new ArrayList<String>();
        idList = new ArrayList<String>();


        adapter = new MenuAdaptor(menuList);
        adapter.setClickListener(this);

        recyclerView = findViewById(R.id.listOfMenu);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Button add = (Button) findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextInputEditText nameInput = findViewById(R.id.menu_input);
                TextInputEditText typeInput = findViewById(R.id.type_input);
                String food = nameInput.getText().toString().trim();
                String type = typeInput.getText().toString().trim();
                if(!food.isEmpty() && !menuList.contains(food)){
                    CookMenu.Menu cur = new CookMenu.Menu();
                    cur.name = food;
                    cur.type=type;
                    databaseReference.child("MenuList").child(cookID).child(String.valueOf(menuId+1)).setValue(cur);
                    Log.d("Click", "id"+id);
                    Toast.makeText(v.getContext(), "Added " + food + "To Menu", Toast.LENGTH_SHORT).show();
                    nameInput.setText("");
                    typeInput.setText("");

                }
                else{
                    Toast.makeText(v.getContext(), "Unable to add " + food, Toast.LENGTH_SHORT).show();
                    refreshMenu();
                }
            }
        });

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

        databaseReference.child("MenuList").child(cookID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(suspend){
                    return;
                }
                menuList.clear();
                typeList.clear();
                idList.clear();
                menuId = 0;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    CookMenu.Menu cur = child.getValue(CookMenu.Menu.class);
                    menuList.add(cur.name);
                    typeList.add(cur.type);
                    idList.add(child.getKey());
                    if(menuId <  Integer.parseInt(child.getKey())){
                        menuId = Integer.parseInt(child.getKey());
                    }
//                    idList.add(child.getKey());
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
        databaseReference.child("Menu").child(cookID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(suspend){
                    return;
                }
                listOfMenu.clear();
                id = 0;
                // This method( is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    int cid = Integer.parseInt(child.getKey());
                    if(cid > id){
                        id = cid;
                    }
                    CookMenu.Menu cur = child.getValue(CookMenu.Menu.class);
                    listOfMenu.add(cur.name);
                    Log.d("DB out",child.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB expection", "Failed to read value.", error.toException());
            }
        });
    }

    private void refreshListOfMenu(){
        databaseReference.child("Menu").child(cookID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listOfMenu.clear();
                id = 0;
                // This method( is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    int cid = Integer.parseInt(child.getKey());
                    if(cid > id){
                        id = cid;
                    }
                    CookMenu.Menu cur = child.getValue(CookMenu.Menu.class);
                    listOfMenu.add(cur.name);
                    Log.d("DB out",child.getKey());
                }
                refreshMenuList();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB expection", "Failed to read value.", error.toException());
            }
        });
    }
    private void refreshMenuList(){
        databaseReference.child("MenuList").child(cookID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuList.clear();
                typeList.clear();
                idList.clear();
                menuId = 0;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    CookMenu.Menu cur = child.getValue(CookMenu.Menu.class);
                    menuList.add(cur.name);
                    typeList.add(cur.type);
                    idList.add(child.getKey());
                    Log.d("DB out",child.getKey());
                    if(menuId <  Integer.parseInt(child.getKey())){
                        menuId = Integer.parseInt(child.getKey());
                    }
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
        if(view.getId() == R.id.purchase_text) {
            String food = adapter.getItem(position);
            String type = typeList.get(position);
            CookMenu.Menu cur = new CookMenu.Menu();
            cur.name = food;
            cur.type = type;
            // Add
            databaseReference.child("Menu").child(cookID).child(String.valueOf(id+1)).setValue(cur);
            id += 1;
            Toast.makeText(this, "Added " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MenuList.this, CookMain.class));
        }
        else{
            String food = adapter.getItem(position);
            String curId = idList.get(position);
            if(!listOfMenu.contains(food)){
                databaseReference.child("MenuList").child(cookID).child(curId).removeValue();
            }
            else{
                Toast.makeText(this, "Unable to remove " + food + "as it is in current offer", Toast.LENGTH_SHORT).show();
            }

        }
//        databaseReference.child("MenuList").child(cookID).removeValue();
//        ComplaintPop popUpClass = new ComplaintPop();
//        popUpClass.showPopupWindow(view,listOfComplaints.get(position),listOfCooks.get(position),listOfClient.get(position),databaseReference);

    }

    public void refreshMenu() {
        adapter.setEnable(!suspend);
        TextView title = (TextView) findViewById(R.id.Menu_title);
        title.setText("Menu (Click to Offer)");
        findViewById(R.id.meal_text).setVisibility(View.VISIBLE);
        findViewById(R.id.meal_text).setEnabled(true);
        findViewById(R.id.type_text).setVisibility(View.VISIBLE);
        findViewById(R.id.type_text).setEnabled(true);
        Button add = (Button) findViewById(R.id.addBtn);
        add.setText("Add To Menu");
        add.setEnabled(!suspend);
        if (suspend) {
            findViewById(R.id.meal_text).setVisibility(View.INVISIBLE);
            findViewById(R.id.meal_text).setEnabled(false);
            findViewById(R.id.type_text).setVisibility(View.INVISIBLE);
            findViewById(R.id.type_text).setEnabled(false);
            menuList.clear();
            menuList.add("You are suspended");
            adapter.notifyDataSetChanged();
            return;
        }
        refreshListOfMenu();
    }

}
