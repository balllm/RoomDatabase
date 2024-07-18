package com.db.roomproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemRecyclerView extends RecyclerView.Adapter<ItemRecyclerView.RecyclerViewHolder> {
    private List<Item> itemList;

    public ItemRecyclerView(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Item item = itemList.get(position);

//        holder.imageView.setImageResource(item.getImage());
        holder.nameView.setText(item.getName());
        holder.descView.setText(item.getDescription());
//        holder.priceView.setText(item.getPrice());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameView;
        public TextView descView;
//        public TextView priceView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameView = itemView.findViewById(R.id.name);
            descView = itemView.findViewById(R.id.description);
//            priceView = itemView.findViewById(R.id.price);
        }
    }
}
