package com.example.project_group_6;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminComplaints extends AppCompatActivity implements ComplaintsAdaptor.ItemClickListener {
        RecyclerView recyclerView;
        ArrayList<String> listOfComplaints;
        ArrayList<String> listOfCooks;
        ArrayList<String> listOfClient;
        ArrayList<String> listOfID;
        //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
        ComplaintsAdaptor adapter;
        LinearLayoutManager layoutManager;
        DatabaseReference databaseReference;

    public static class Complain {

        public String client;
        public String cook;
        public String content;
        public Complain(){}

    }


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-7629811168995371601-default-rtdb.firebaseio.com/");
            setContentView(R.layout.list_of_complaints);

            listOfComplaints = new ArrayList<String>();
            listOfCooks =  new ArrayList<String>();
            listOfClient = new ArrayList<String>();
            listOfID = new ArrayList<String>();
            // set up the RecyclerView
            recyclerView = findViewById(R.id.ListofComplaints);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);


            adapter = new ComplaintsAdaptor(listOfComplaints,listOfCooks);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    layoutManager.getOrientation());

            dividerItemDecoration.setDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.addItemDecoration(dividerItemDecoration);

//            setUpComplaints();
            // Read from the database
            databaseReference.child("Complaints").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listOfComplaints.clear();
                    listOfID.clear();
                    listOfCooks.clear();
                    listOfClient.clear();

                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String id = child.getKey();
                        Complain c = child.getValue(Complain.class);
                        listOfID.add(id);
                        listOfClient.add(c.client);
                        listOfCooks.add(c.cook);
                        listOfComplaints.add(c.content);
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

        ComplaintPop popUpClass = new ComplaintPop();
        popUpClass.showPopupWindow(view,listOfComplaints.get(position),listOfCooks.get(position),listOfID.get(position),databaseReference);
//        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}