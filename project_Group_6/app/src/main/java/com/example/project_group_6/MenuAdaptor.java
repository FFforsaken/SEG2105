package com.example.project_group_6;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MenuAdaptor extends RecyclerView.Adapter<MenuAdaptor.ViewHolder>{
    private ArrayList<String> menus;
    private ItemClickListener itemClickListener;
    private boolean enable,clickable;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView food;
        private Button remove;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            food = (TextView) view.findViewById(R.id.menu_text);
            remove = (Button) view.findViewById(R.id.remove);
            remove.setOnClickListener(this);
            food.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }

        public TextView getFood() {
            return food;
        }
        public Button getRemove() {
            return remove;
        }
    }


    public MenuAdaptor(ArrayList<String> dataSet) {
        this.menus = dataSet;
        this.enable = true;
        this.clickable=true;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getFood().setText(getItem(position));
        viewHolder.getFood().setClickable(clickable);
        viewHolder.getRemove().setEnabled(enable);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return menus.size();
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return menus.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public void setEnable(boolean enable){
        this.enable=enable;

    }
    public void setClickable(boolean clickable){
        this.clickable=clickable;

    }

    public boolean getEnable(){return enable; }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}