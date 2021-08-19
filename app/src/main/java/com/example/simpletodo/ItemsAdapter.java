package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

// ItemsAdapter's goal is to display data to the recyclerview
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.viewHolder>{

    // an interface that communicates from main activity to Items Adapter
    public interface OnLongClickListener{
        void onLongClickListener(int pos);
    }
    List<String> Items;
    OnLongClickListener longClickListener;

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener){
        this.Items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    // ViewHolder is like a container that keeps each views in a represented list.
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate a view and wrap it around a view holder
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent, false);
        return new viewHolder(todoView);
    }

    @Override
    // Binds the view holder with data
    public void onBindViewHolder(@NonNull ItemsAdapter.viewHolder holder, int position) {

        String item = Items.get(position);
        holder.bind(item);
    }

    @Override
    // returns the size of the list in the recycler view
    public int getItemCount() {
        return Items.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public viewHolder(@NonNull View itemView) {

            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item) {
            textView.setText(item);
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // returns the exact position of the view which has been clicked on the recycler
                    // view
                    longClickListener.onLongClickListener(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
