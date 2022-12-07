package com.example.project_group_6;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ComplaintPop {

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.complaint_pop);
//
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//        getWindow().setLayout((int)(width*0.4),(int)(height*0.3));
//
//    }


    //PopupWindow display method

    public void showPopupWindow(final View view, String complaint, String cook, String id, DatabaseReference databaseReference) {


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.complaint_pop, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler
        TextView title = popupView.findViewById(R.id.pop_title);
        title.setText(complaint);

        Button dismiss = popupView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Remove value
                databaseReference.child("Complaints").child(id).removeValue();
                popupWindow.dismiss();
            }
        });

        Button suspend = popupView.findViewById(R.id.suspend);
        suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //suspend user
                databaseReference.child("cook_accounts").child(cook).child("_suspend").setValue(true);
                //Remove value
                databaseReference.child("Complaints").child(id).removeValue();
                popupWindow.dismiss();
            }
        });



        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }

}
