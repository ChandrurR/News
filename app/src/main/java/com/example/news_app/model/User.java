package com.example.news_app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User { // table
    @NonNull
    @PrimaryKey
    public String phone;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "password")
    public String password;

    public User(String phone, String name, String password){
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

}
