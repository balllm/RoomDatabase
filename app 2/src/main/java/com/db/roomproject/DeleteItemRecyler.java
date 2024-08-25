package com.db.roomproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class DeleteItemRecyler extends RecyclerView.Adapter<DeleteItemRecyler.RecyclerViewHolder> {
    private List<Item> itemList = new ArrayList<>();
    private ItemDao itemDao;
    private ExecutorService executorService;
//    public DeleteItemRecyler(List<Item> itemList) {
//        this.itemList = itemList;
//    }
    public DeleteItemRecyler(List<Item> itemList, ItemDao itemDao, ExecutorService executorService) {
        this.itemList = itemList;
        this.itemDao = itemDao;
        this.executorService = executorService;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_item, parent, false);
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

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        itemDao.delete(item);
                        itemList.remove(position);
                        notifyItemRemoved(position);
                    }
                });
            }
        });
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UpdateActivity.class);
                intent.putExtra("itemName", item.getName());
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameView;
        public TextView descView;
        public Button deleteBtn;
        public Button updateBtn;
        public RecyclerViewHolder(View view) {
            super(view);
            imageView = itemView.findViewById(R.id.image);
            nameView = itemView.findViewById(R.id.name);
            descView = itemView.findViewById(R.id.description);
            deleteBtn = itemView.findViewById(R.id.button);
            updateBtn = itemView.findViewById(R.id.button2);
        }
    }

}
