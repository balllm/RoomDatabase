package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView fastfoodRecycler;
    ArrayList<Item> fastfoodList;
    RecyclerView saladsRecycleer;
    ArrayList<Item> saladsList;
    ItemRecyclerView fastfoodAdapter;
    ItemRecyclerView saladsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycler();
        initMenu();

    }
   public void initRecycler(){
       fastfoodRecycler = findViewById(R.id.recyclerView);

       fastfoodList = new ArrayList<>();
       fastfoodRecycler.setHasFixedSize(true);
       fastfoodAdapter = new ItemRecyclerView(fastfoodList);
       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
       fastfoodRecycler.setAdapter(fastfoodAdapter);
       fastfoodRecycler.setLayoutManager(layoutManager);

       saladsRecycleer = findViewById(R.id.recyclerView2);

       saladsList = new ArrayList<>();
       saladsRecycleer.setHasFixedSize(true);
       saladsAdapter = new ItemRecyclerView(saladsList);
       RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
       saladsRecycleer.setAdapter(saladsAdapter);
       saladsRecycleer.setLayoutManager(layoutManager2);
   }
   public void initMenu(){
        fastfoodList.add(new Item("Burger", "Лучший бургер", 1000, R.drawable.burger));
        fastfoodList.add(new Item("Pizza", "Лучшая пицца", 1200, R.drawable.pizza));
        fastfoodList.add(new Item("Hot dog", "Лучший хот дог", 800, R.drawable.hotdog));
        fastfoodList.add(new Item("Fires", "Картошка фри", 400, R.drawable.free));

        saladsList.add(new Item("Ceaser salad", "Лучший салат", 700, R.drawable.ceaser));
        saladsList.add(new Item("Salad with vegetables", "Лучший салат", 650, R.drawable.vegetables));
        saladsList.add(new Item("Salad with fruits", "Лучший салат", 650, R.drawable.fruit));
        saladsList.add(new Item("Salad with chicker", "Лучший салат", 750, R.drawable.chickersalad));
   }
}
