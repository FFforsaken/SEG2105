package com.example.project_group_6;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    Button btn_logout_client;
    Button btn_update_p;
    TextView first,last,email,password,address;
    String clientID;
    DatabaseReference databaseReference;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        btn_logout_client = v.findViewById(R.id.btn_logout_client);
        btn_update_p = v.findViewById(R.id.btn_update_p);


        btn_logout_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectToLogin.class);
                startActivity(intent);
            }
        });

        btn_update_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child("client_accounts").child(clientID).child("_last_name").setValue(last.getText().toString());
                databaseReference.child("client_accounts").child(clientID).child("_first_name").setValue(first.getText().toString());
                databaseReference.child("client_accounts").child(clientID).child("_account_password").setValue(password.getText().toString());
                databaseReference.child("client_accounts").child(clientID).child("_address").setValue(address.getText().toString());
                databaseReference.child("client_accounts").child(clientID).child("_email_address").setValue(email.getText().toString());
                Toast. makeText(getContext(),"Update succeed",Toast. LENGTH_SHORT).show();
                // update the information

            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-7629811168995371601-default-rtdb.firebaseio.com/");


        SharedPreferences prefs = getContext().getSharedPreferences("UserID", MODE_PRIVATE);
        clientID = prefs.getString("ClientID", "");
        last = v.findViewById(R.id.et_lastname_profile);
        first = v.findViewById(R.id.et_firstname_profile);
        email = v.findViewById(R.id.et_username_profile);
        password = v.findViewById(R.id.et_password_profile);
        address = v.findViewById(R.id.et_address_profile);

        databaseReference.child("client_accounts").child(clientID).addValueEventListener(new ValueEventListener() {
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




        return v;
    }
}