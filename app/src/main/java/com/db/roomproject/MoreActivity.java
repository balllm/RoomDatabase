package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MoreActivity extends AppCompatActivity {
    String itemName;
    ImageView imageView;
    TextView title;
    TextView price;
    TextView description;
    Button buyBtn;
    ItemDao itemDao;
    Item item;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        init();
        itemName = getIntent().getStringExtra("itemName" );

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                item = itemDao.getItemByTitle(itemName);

                title.setText(item.getName());
                description.setText(item.getDescription());
                price.setText(String.valueOf(item.getPrice()));


//                Glide.with(MoreActivity.this)
//                        .load(item.getImage())
//                        .into(imageView);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }
    public void init() {
        imageView = findViewById(R.id.moreImg);
        title = findViewById(R.id.titleMore);
        price = findViewById(R.id.priceMore);
        description = findViewById(R.id.descMore);
        buyBtn = findViewById(R.id.buyButton);
        itemDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DbConfig.ROOM_DB_NAME)
                .build()
                .itemDao();
    }
}