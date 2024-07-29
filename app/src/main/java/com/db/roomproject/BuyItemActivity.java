package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BuyItemActivity extends AppCompatActivity {
    TextView orderList;
    String showList = "";
    int showPrice = 0;
    OrderDao orderDao;
    List<Order> order;
    TextView finalPrice;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_item);

        orderDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DbConfig.ROOM_DB_NAME)
                //                .fallbackToDestructiveMigration()
                .build()
                .orderDao();
        orderList = findViewById(R.id.order);
        finalPrice = findViewById(R.id.finalPrice);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                order = orderDao.getOrdersByEmailo(ConfigUser.EMAIL_USER);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Order elem: order) {
                            showList += elem.item_name + " " + elem.price + " x " + elem.item_count + " шт " +
                                    " Итого: " + elem.item_count * elem.price + " тг " +"\n";
                            showPrice += elem.item_count * elem.price;
                        }
                        orderList.setText(showList);
                        finalPrice.setText(String.valueOf(showPrice) + " тг");
                    }
                });
            }
        });
    }
    public void taptomaina(View view) {
        Intent intent = new Intent(BuyItemActivity.this, MainActivity.class);
        intent.putExtra("email", ConfigUser.EMAIL_USER);
        startActivity(intent);
    }
}