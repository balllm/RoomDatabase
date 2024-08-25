package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Пример MainActivity
public class MainActivity extends AppCompatActivity {

    private RecyclerView itemRecycler;
    private ItemRecyclerView itemAdapter;
    private ItemDao itemDao;
    FloatingActionButton floatingActionButton;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        Bundle argument = getIntent().getExtras();
        if(argument != null){
            ConfigUser.EMAIL_USER = argument.get("email").toString();
            Toast.makeText(this, "Hello " + ConfigUser.EMAIL_USER, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Email не найден", Toast.LENGTH_SHORT).show();
        }

        floatingActionButton = findViewById(R.id.floatingBtn);
        if(ConfigUser.EMAIL_USER.equals("admin@mail.ru")){
            floatingActionButton.setVisibility(View.VISIBLE);
        }else{
            floatingActionButton.setVisibility(View.GONE);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });
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
