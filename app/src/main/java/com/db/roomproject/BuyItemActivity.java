package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BuyItemActivity extends AppCompatActivity {
    String showList = "";
    int showPrice = 0;
    OrderDao orderDao;
    List<Order> order;
    int sum = 0;

    Button confirmBtn;
    RecyclerView recyclerView;
    DeleteOrderRecycler orderAdapter;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_item);

        orderDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DbConfig.ROOM_DB_NAME)
                .build()
                .orderDao();
        recyclerView = findViewById(R.id.order_deleteRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        ConfigUser.TEXT_VIEW = findViewById(R.id.finalPrice);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                order = orderDao.getOrdersByEmailo(ConfigUser.EMAIL_USER);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        orderAdapter = new DeleteOrderRecycler(order, orderDao, executorService);
                        recyclerView.setAdapter(orderAdapter);

                        for (Order elem:order) {
                            sum += elem.getItem_count() * elem.getPrice();
                        }
                        ConfigUser.TEXT_VIEW.setText(String.valueOf(sum));
                    }
                });
            }
        });
        confirmBtn = findViewById(R.id.confirmBTN);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail("Order 1", "Ваш заказ в пути: ", "test@mail.ru");
            }
        });
    }
    public void sendEmail(String subject, String content, String email){
        Intent intentEmail = new Intent(Intent.ACTION_SEND);
        intentEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intentEmail.putExtra(Intent.EXTRA_SUBJECT, subject);
        intentEmail.putExtra(Intent.EXTRA_TEXT, content);
        intentEmail.setType("message/rfc822");
        startActivity(Intent.createChooser(intentEmail, "Send E-mail"));


    }
    public void taptomaina(View view) {
        Intent intent = new Intent(BuyItemActivity.this, MainActivity.class);
        intent.putExtra("email", ConfigUser.EMAIL_USER);

        startActivity(intent);
    }
}