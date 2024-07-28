package com.db.roomproject;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = DbConfig.ORDER_TABLE)
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "login_id")
    public String login_id;
    @ColumnInfo(name = "item_name")
    public String item_name;
    @ColumnInfo(name = "item_count")
    public int item_count;
    @ColumnInfo(name = "price")
    public int price;

    public String getLogin_id() {
        return login_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public int getItem_count() {
        return item_count;
    }

    public int getPrice() {
        return price;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
