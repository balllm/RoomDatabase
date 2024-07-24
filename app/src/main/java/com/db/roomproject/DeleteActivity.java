package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeleteActivity extends AppCompatActivity {

    private RecyclerView itemRecycler;
    private DeleteItemRecyler deleteAdapter;
    Button button;
    ItemDao itemDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        init();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final List<Item> itemList = itemDao.getAllItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        deleteAdapter = new DeleteItemRecyler(itemList, itemDao, executorService);
                        itemRecycler.setAdapter(deleteAdapter);
                    }
                });
            }
        });
        button = findViewById(R.id.taptomain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
                startActivity(intent);
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