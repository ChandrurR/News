package com.example.news_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.news_app.model.AppDatabase;
import com.example.news_app.model.User;
import com.example.news_app.util.AppPreferences;

public class Login_Activity extends AppCompatActivity
{
    TextView signin,forgetpassword,register;
    EditText username, password;
    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layoout);


        signin = findViewById(R.id.signin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(username.getText().toString().isEmpty() || username.getText().toString().length()<10){
                    Toast.makeText(Login_Activity.this,"Please enter valid number",Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().isEmpty()){
                    Toast.makeText(Login_Activity.this,"Please enter password",Toast.LENGTH_SHORT).show();
                }else
                    {

                    validateLogin();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this,RegisterActivity.class));
                finish();
            }
        });


    }

    public void validateLogin() {


        User userList= AppDatabase.getDb(Login_Activity.this).userDao().findByNamePass(username.getText().toString(),
                password.getText().toString());

        if(userList != null){

            Toast.makeText(Login_Activity.this,"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login_Activity.this,MainActivity.class));
            new AppPreferences(Login_Activity.this).setValue(AppPreferences.IS_USER_LOGGED,userList.name);
            finish();
        }else {
            userList= AppDatabase.getDb(Login_Activity.this).userDao().findByName(username.getText().toString());
            if(userList == null){
                Toast.makeText(Login_Activity.this,"User not found Please register",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Login_Activity.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
