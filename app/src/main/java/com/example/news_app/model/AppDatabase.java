package com.example.news_app.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public static AppDatabase getDb(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "news_app_db").allowMainThreadQueries().build();
    }
}



