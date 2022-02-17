package com.example.news_app.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {  // user table query
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE phone = :phone AND " +
            "password = :password")
    User findByNamePass(String phone, String password);

    @Query("SELECT * FROM user WHERE phone = :phone")
    User findByName(String phone);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}

