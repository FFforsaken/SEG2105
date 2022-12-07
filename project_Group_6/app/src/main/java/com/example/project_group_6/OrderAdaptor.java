package com.example.project_group_6;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class OrderAdaptor extends RecyclerView.Adapter<OrderAdaptor.ViewHolder>{
    private ArrayList<String> orders;
    private ArrayList<String> clients;
    private ItemClickListener itemClickListener;
    private boolean enable,clickable;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView meal;
        private TextView client;
        private Button accept;
        private Button decline;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            meal = (TextView) view.findViewById(R.id.meal_name);
            client = (TextView) view.findViewById(R.id.order_client);
            accept = view.findViewById(R.id.order_accept);
            decline = view.findViewById(R.id.order_decline);
            accept.setOnClickListener(this);
            decline.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }

        public TextView getMeal() {
            return meal;
        }
        public TextView getClient() {
            return client;
        }
    }


    public OrderAdaptor(ArrayList<String> orders,ArrayList<String> clients) {
        this.orders = orders;
        this.clients = clients;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.order_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getMeal().setText(getMealItem(position));
        viewHolder.getClient().setText(getClientsItem(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orders.size();
    }

    // convenience method for getting data at click position
    String getMealItem(int id) {
        return orders.get(id);
    }

    String getClientsItem(int id) {
        return clients.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}