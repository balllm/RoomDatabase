package com.db.roomproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class DeleteOrderRecycler extends RecyclerView.Adapter<DeleteOrderRecycler.RecyclerViewHolder> {
    private List<Order> orderList = new ArrayList<>();
    private OrderDao orderDao;
    private ExecutorService executorService;


    public DeleteOrderRecycler(List<Order> orderList, OrderDao orderDao, ExecutorService executorService) {
        this.orderList = orderList;
        this.orderDao = orderDao;
        this.executorService = executorService;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_order, parent, false);
        return new DeleteOrderRecycler.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteOrderRecycler.RecyclerViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderTitle.setText(order.getItem_name().toString());
        holder.orderPrice.setText(String.valueOf(order.getPrice()));
        holder.orderCount.setText(String.valueOf(order.getItem_count()));



        holder.orderDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfigUser.TEXT_VIEW.setText("0");


                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        orderDao.delete(order);
                        orderList.remove(position);
                        notifyItemRemoved(position);

                        List<Order> list = orderDao.getOrdersByEmailo(ConfigUser.EMAIL_USER);
                        int sum = 0;
                        for (Order elem:list) {
                            sum += elem.getItem_count() * elem.getPrice();
                        }
                        ConfigUser.TEXT_VIEW.setText(String.valueOf(sum));
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView orderTitle;
        public TextView orderPrice;
        public TextView orderCount;
        public Button orderDelete;

        public RecyclerViewHolder(View view) {
            super(view);
            orderTitle =  itemView.findViewById(R.id.order_title);
            orderPrice =  itemView.findViewById(R.id.order_price);
            orderCount = itemView.findViewById(R.id.order_count);
            orderDelete =  itemView.findViewById(R.id.order_delete);
        }
    }

}
