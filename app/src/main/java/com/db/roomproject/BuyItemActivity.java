package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class BuyItemActivity extends AppCompatActivity {
    TextView orderList;
    ArrayList<String> addItems;
    String showList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_item);

        addItems =  (ArrayList<String>)getIntent().getStringArrayListExtra("ITEMS_TO_SEND");
        orderList = findViewById(R.id.order);

        showList = "";

        for (String elem : addItems) {
            String[] str = elem.split("#");
            showList += str[0] + " " + str[1] + " шт " + str[2] + " тг " + "\n";
        }
        orderList.setText(showList);

    }
    public void taptomaina(View view) {
        Intent intent = new Intent(BuyItemActivity.this, MainActivity.class);
        startActivity(intent);
    }
}