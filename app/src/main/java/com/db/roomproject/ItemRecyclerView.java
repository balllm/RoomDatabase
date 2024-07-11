package com.db.roomproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemRecyclerView extends RecyclerView.Adapter<ItemRecyclerView.RecyclerViewHolder> {
    private ArrayList<Item> arrayList;
    public ItemRecyclerView(ArrayList<Item> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false);
        return new RecyclerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerView.RecyclerViewHolder holder, int position) {
        Item item = arrayList.get(position);

        holder.imageView.setImageResource(item.getIamge());
        holder.nameView.setText(item.getName());
        holder.descView.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameView;
        public TextView descView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameView = itemView.findViewById(R.id.name);
            descView = itemView.findViewById(R.id.description);
        }
    }
}
