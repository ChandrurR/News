package com.example.news_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.news_app.model.AppDatabase;
import com.example.news_app.model.User;
import com.example.news_app.util.AppPreferences;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
           @Override
           public void run()
           {
            gotoHome();

           }
       }, 3000);


}


    public void gotoHome()
    {

        //insert

        User userList= AppDatabase.getDb(this).userDao().findByName("9597217951");

        Log.d("DB",userList.password+" "+userList.name);

        // move to home activity.

        if(new AppPreferences(SplashActivity.this).getValue(AppPreferences.IS_USER_LOGGED).isEmpty()){
            Intent intent=new Intent(SplashActivity.this, Login_Activity.class);//move activitty
            startActivity(intent);
            finish();

        }else
            {

            Intent intent=new Intent(SplashActivity.this, MainActivity.class);//move activitty
            startActivity(intent);
            finish();
        }

    }
}