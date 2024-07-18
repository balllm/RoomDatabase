package com.db.roomproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    // Dao = database operation
    @Insert
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table WHERE email = :emailId")
    User getUserByLoginId(String emailId);
    @Query("SELECT * FROM user_table WHERE email = :emailId AND password = :passwordId")
    User getUserByEmailAndPassword(String emailId, String passwordId);
    @Query("SELECT * FROM " + DbConfig.USER_TABLE)
    List<User> getAllUsers();

}
