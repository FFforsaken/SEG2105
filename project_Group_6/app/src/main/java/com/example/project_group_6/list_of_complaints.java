package com.example.project_group_6;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class list_of_complaints extends AppCompatActivity {
        ListView list_view;
        ArrayList<String> list;

        Button btn_Dismiss, btn_indefinitely, btn_temporarily;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.list_of_complaints);

            list_view = (ListView) findViewById(R.id.list_vieww);
            list = new ArrayList<String>();


            ///只是例子，需要加到database
            //However, given that Clients cannot yet purchase meals and submit complaints at this stage, you will pre-create a list
            //of complaints stored in the DB. Each complaint should be associated with a registered Cook.
            list.add("i dont like this food");

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);

            list_view.setAdapter(arrayAdapter);// 在UI显示

            onItemLongClick();
        }



        /////////////////////当按了list view里的一个complaint后，显示一个弹出的界面，里面包含了三个解决的方法
        private void onItemLongClick() {

            list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ///需要加东西
                    return false;
                }

            });
        }
        private void show3way_of_handleDialog(String s){ ///这里需要改的，请参考Lab5的文件，参数需要的是具体的投诉来源，参数可以是具体的客户和具体的厨师
            
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.choose_how_to_handle, null);
            dialogBuilder.setView(dialogView);

            
            btn_Dismiss = (Button) dialogView.findViewById(R.id.btn_dismiss);
            btn_indefinitely = (Button) dialogView.findViewById(R.id.btn_indefinitely);
            btn_temporarily = (Button) dialogView.findViewById(R.id.btn_temporarily);

            
            dialogBuilder.setTitle(s);
            final AlertDialog b = dialogBuilder.create();
            b.show();

            ///////////////////////////////////////////////////////////////////////////////////
            btn_Dismiss.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    // do something that delete this complain from DB and from the UI(xml file)
                    b.dismiss();
                }
            });

            btn_indefinitely.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                // 找到客户投诉的对应的厨师，然后把这个厨师的账户永久暂停（从DB里删除这个账号）,可能需要把indefinitely_Closed设置为true（从DB pull）
                // 然后在LoginActivityCook.java里的122行后判定这个account是否永久暂停
                // 如果是的话当他们按了login in 按钮后信息弹出说明是永久暂停此号
                // if (getPasswordfromDB.equals(UserEntered_password))
                //    if(indefinitely_Closed == false)
                //        if (available == true) // 三个if过了就可以进入cook的主界面
                    b.dismiss();
                }
            });

            btn_temporarily.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                // 找到客户投诉的对应的厨师，然后把这个厨师的账户暂时暂停,可能需要设置对应的cook的available到false；然后在 LoginActivityCook.java 里的122行下加多个if判定
                // 如果状态是true的才能进入welcome，如果不是则需要有提示信息弹出说明是暂时暂停此号
                    b.dismiss();
                }
            });

        }


}