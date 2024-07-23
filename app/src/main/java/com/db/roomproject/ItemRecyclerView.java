package com.db.roomproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemRecyclerView extends RecyclerView.Adapter<ItemRecyclerView.RecyclerViewHolder> {
    private List<Item> itemList;
    ItemDao itemDao;

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

        Glide.with(holder.itemView.getContext())
                .load(item.getImage())
                .into(holder.imageView);

        holder.nameView.setText(item.getName());
        holder.descView.setText(item.getDescription());

        //        holder.priceView.setText(item.getPrice());
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MoreActivity.class);
                intent.putExtra("itemName", item.getName());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameView;
        public TextView descView;
        public Button moreBtn;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameView = itemView.findViewById(R.id.name);
            descView = itemView.findViewById(R.id.description);
            moreBtn = itemView.findViewById(R.id.moreBtn);
        }
    }
}
