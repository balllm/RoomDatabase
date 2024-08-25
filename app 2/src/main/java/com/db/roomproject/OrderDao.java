package com.db.roomproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao

public interface OrderDao {
    @Insert
    void insert(Order order);
    @Update
    void update(Order order);
    @Delete
    void delete(Order order);

    @Query("SELECT * FROM order_table WHERE login_id = :loginId")
    Order getOrderByEmailo(String loginId);
    @Query("SELECT * FROM order_table WHERE item_name = :nameId")
    Order getOrderByNameId(String nameId);
    @Query("SELECT * FROM order_table WHERE login_id = :loginId")
    List<Order> getOrdersByEmailo(String loginId);
    @Query("SELECT * FROM " + DbConfig.ORDER_TABLE)
    List<Order> getAllOrders();
}
