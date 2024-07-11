package com.db.roomproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = DbConfig.ITEM_TABLE)
public class Item {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "price")
    public int price;
    @ColumnInfo(name = "image")
    public int iamge;

    public Item(String name, String description, int price, int iamge) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.iamge = iamge;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getIamge() {
        return iamge;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setIamge(int iamge) {
        this.iamge = iamge;
    }
}
