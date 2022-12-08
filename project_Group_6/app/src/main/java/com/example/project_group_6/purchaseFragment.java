package com.example.project_group_6;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link purchaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class purchaseFragment extends Fragment implements PurchasesAdaptor.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    String clientsID;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    PurchasesAdaptor adapter;
    LinearLayoutManager layoutManager;
    ArrayList<String> orders,status;
    boolean suspend;

    public purchaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment purchaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static purchaseFragment newInstance(String param1, String param2) {
        purchaseFragment fragment = new purchaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        past_orders

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-7629811168995371601-default-rtdb.firebaseio.com/");
        View view =  inflater.inflate(R.layout.fragment_purchase, container, false);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("UserID", MODE_PRIVATE);
        clientsID = prefs.getString("ClientID", "");
        orders = new ArrayList<>();
        status = new ArrayList<>();
        adapter = new PurchasesAdaptor(orders,status);
        adapter.setClickListener(this);

        recyclerView = view.findViewById(R.id.past_orders);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Log.d("TAG", "onCreateView: " + clientsID);

        databaseReference.child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orders.clear();
                status.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    CookOrder.Order cur = child.getValue(CookOrder.Order.class);
                    if(cur.accept){
                        orders.add(cur.meal + ", cook: " + cur.cook + ", status: accept");
                        status.add("true");

                    }
                    else{
                        if(cur.decline){
                            orders.add(cur.meal + ", cook: " + cur.cook + ", status: decline");
                            status.add("false");
                        }
                        else{
                            if(cur.complete){
                                orders.add(cur.meal + ", cook: " + cur.cook + ", status: decline");
                                status.add("false");
                            }
                            else{
                                orders.add(cur.meal + ", cook: " + cur.cook + ", status: pending");
                                status.add("false");
                            }
                        }
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

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}