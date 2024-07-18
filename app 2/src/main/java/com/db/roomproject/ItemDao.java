package com.db.roomproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ItemDao {
    @Insert
    void insert(Item item);
    @Update
    void update(Item item);
    @Delete
    void delete(Item item);

    @Query("SELECT * FROM item_table WHERE name = :titleId")
    Item getItemByTitle(String titleId);
    @Query("SELECT * FROM " + DbConfig.ITEM_TABLE)
    List<Item> getAllItems();
}
