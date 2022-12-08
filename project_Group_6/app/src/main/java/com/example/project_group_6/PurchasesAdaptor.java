package com.example.project_group_6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PurchasesAdaptor extends RecyclerView.Adapter<PurchasesAdaptor.ViewHolder>{
    private ArrayList<String> orders;
    private ArrayList<String> status;
    private ItemClickListener itemClickListener;
    private boolean enable,clickable;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView purchase;
        private Button rate;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            purchase = (TextView) view.findViewById(R.id.purchase_text);
            rate = view.findViewById(R.id.btn_rate);

        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }

        public TextView getPurchase() {
            return purchase;
        }
        public Button getRate() {
            return rate;
        }
    }


    public PurchasesAdaptor(ArrayList<String> orders,ArrayList<String> status) {
        this.orders = orders;
        this.status = status;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.purchase_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getPurchase().setText(getOrderItem(position));
        if(getStatus(position).equals("false")){
            viewHolder.getRate().setEnabled(false);
        }
        else {
            viewHolder.getRate().setEnabled(true);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orders.size();
    }

    // convenience method for getting data at click position
    String getOrderItem(int id) {
        return orders.get(id);
    }

    String getStatus(int id) {
        return status.get(id);
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
