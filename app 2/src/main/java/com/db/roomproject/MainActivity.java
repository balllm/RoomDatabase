package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Пример MainActivity
public class MainActivity extends AppCompatActivity {

    private RecyclerView itemRecycler;
    private ItemRecyclerView itemAdapter;
    private ItemDao itemDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final List<Item> itemList = itemDao.getAllItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        itemAdapter = new ItemRecyclerView(itemList);
                        itemRecycler.setAdapter(itemAdapter);
                    }
                });
            }
        });
    }
    public void init(){
        itemRecycler = findViewById(R.id.recyclerView);
        itemRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        itemRecycler.setHasFixedSize(true);
        itemDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DbConfig.ROOM_DB_NAME)
//                .fallbackToDestructiveMigration()
                .build().itemDao();

    }

}
