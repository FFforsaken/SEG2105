package com.example.project_group_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Search_activity extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference offerReference;
    private SearchView searchView;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = findViewById(R.id.ListView);
        searchView =  findViewById(R.id.searchView);
        List<HashMap<String, String>> data = new LinkedList<>();
        offerReference = FirebaseDatabase.getInstance().getReference("Menu/");
        offerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {

                    for (DataSnapshot child2 : child.getChildren()) {
                        HashMap<String, String> map = new HashMap<>();
                        String temp = child2.getValue().toString().substring(6);
                        map.put("meal", temp);
                        data.add(map);

                    }
                }

                adapter = new SimpleAdapter(getApplicationContext(), data,
                        R.layout.list_of_menu, new String[]{"meal"}, new int[]{R.id.mealName_TV});
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView mealName;
                mealName = (TextView) findViewById(R.id.mealName_TV);
                String meal =   mealName.getText().toString();

                Intent intent = new Intent(getApplicationContext(), PurchaseConfirmActivity.class);
                intent.putExtra("meal", meal);
                startActivity(intent);
            }
        });

    }


}