package com.example.project_group_6;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.java_classes.Client;

import com.example.project_group_6.R;

import java.util.List;

public class ClientList{ //extends ArrayAdapter<Client>

    private Activity context;
    List<Client> clients;

    public ClientList(Activity context, List<Client> clients) {
//        super(context, R.layout.layout_client_list, clients); //!!!!!!!!!!!!!!!!!!!! havent crate this layout_client_list, xml
        this.context = context;
        this.clients = clients;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        
        LayoutInflater inflater = context.getLayoutInflater();
//        View listViewItem = inflater.inflate(R.layout.layout_client_list, null, true);

//        TextView etFirstname = (TextView) listViewItem.findViewById(R.id.et_firstname_client);
        // so on
        // ........

        Client client = clients.get(position);
        
        
//        etFirstname.setText(client.get_first_name());
        
//        return listViewItem;
        return null;
    }


}
