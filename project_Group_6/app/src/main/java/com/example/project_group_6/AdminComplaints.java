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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

        //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
        ComplaintsAdaptor adapter;
        LinearLayoutManager layoutManager;
        DatabaseReference databaseReference;


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-7629811168995371601-default-rtdb.firebaseio.com/");
            setContentView(R.layout.list_of_complaints);

            listOfComplaints = new ArrayList<String>();
            listOfCooks =  new ArrayList<String>();
            listOfClient = new ArrayList<String>();
            // set up the RecyclerView
            recyclerView = findViewById(R.id.ListofComplaints);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);


            adapter = new ComplaintsAdaptor(listOfComplaints);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    layoutManager.getOrientation());

            dividerItemDecoration.setDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.addItemDecoration(dividerItemDecoration);

//            setUpComplaints();
            // Read from the database
            databaseReference.child("complaints").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listOfComplaints.clear();
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String cook = child.getKey();
                        for(DataSnapshot c : child.getChildren()){
                            listOfComplaints.add(c.getValue(String.class));
                            listOfCooks.add(cook);
                            listOfClient.add(c.getKey());
                            Log.d("DB out",cook);
                            Log.d("DB out",c.getValue(String.class));
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


            //However, given that Clients cannot yet purchase meals and submit complaints at this stage, you will pre-create a list
            //of complaints stored in the DB. Each complaint should be associated with a registered Cook.
//            onItemLongClick();
        }


    @Override
    public void onItemClick(View view, int position) {

        ComplaintPop popUpClass = new ComplaintPop();
        popUpClass.showPopupWindow(view,listOfComplaints.get(position),listOfCooks.get(position),listOfClient.get(position),databaseReference);
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }



//        /////////////////////当按了list view里的一个complaint后，显示一个弹出的界面，里面包含了三个解决的方法
//        private void onItemLongClick() {
//
//            list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//                @Override
//                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    ///需要加东西
//                    return false;
//                }
//
//            });
//        }
//        private void show3way_of_handleDialog(String s){ ///这里需要改的，请参考Lab5的文件，参数需要的是具体的投诉来源，参数可以是具体的客户和具体的厨师
//
//            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//            LayoutInflater inflater = getLayoutInflater();
//            final View dialogView = inflater.inflate(R.layout.choose_how_to_handle, null);
//            dialogBuilder.setView(dialogView);
//
//
//            btn_Dismiss = (Button) dialogView.findViewById(R.id.btn_dismiss);
//            btn_indefinitely = (Button) dialogView.findViewById(R.id.btn_indefinitely);
//            btn_temporarily = (Button) dialogView.findViewById(R.id.btn_temporarily);
//
//
//            dialogBuilder.setTitle(s);
//            final AlertDialog b = dialogBuilder.create();
//            b.show();
//
//            ///////////////////////////////////////////////////////////////////////////////////
//            btn_Dismiss.setOnClickListener(new View.OnClickListener(){
//                public void onClick(View view){
//                    // do something that delete this complain from DB and from the UI(xml file)
//                    b.dismiss();
//                }
//            });
//
//            btn_indefinitely.setOnClickListener(new View.OnClickListener(){
//                public void onClick(View view){
//                // 找到客户投诉的对应的厨师，然后把这个厨师的账户永久暂停（从DB里删除这个账号）,可能需要把indefinitely_Closed设置为true（从DB pull）
//                // 然后在LoginActivityCook.java里的122行后判定这个account是否永久暂停
//                // 如果是的话当他们按了login in 按钮后信息弹出说明是永久暂停此号
//                // if (getPasswordfromDB.equals(UserEntered_password))
//                //    if(indefinitely_Closed == false)
//                //        if (available == true) // 三个if过了就可以进入cook的主界面
//                    b.dismiss();
//                }
//            });
//
//            btn_temporarily.setOnClickListener(new View.OnClickListener(){
//                public void onClick(View view){
//                // 找到客户投诉的对应的厨师，然后把这个厨师的账户暂时暂停,可能需要设置对应的cook的available到false；然后在 LoginActivityCook.java 里的122行下加多个if判定
//                // 如果状态是true的才能进入welcome，如果不是则需要有提示信息弹出说明是暂时暂停此号
//                    b.dismiss();
//                }
//            });
//
//        }


}