package com.example.news_app;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.news_app.model.AppDatabase;
import com.example.news_app.model.User;

public class RegisterActivity extends AppCompatActivity {
    TextView WelcomeToNewsApp;
    EditText username, pasword, phone, confirmpass;
    Button register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_layout);

        WelcomeToNewsApp = findViewById(R.id.welcome);
        username = findViewById(R.id.userreg);
        pasword = findViewById(R.id.passreg);
        confirmpass = findViewById(R.id.conpassreg);
        phone = findViewById(R.id.phonereg);
        register = findViewById(R.id.Regreg);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                if (username.getText().toString().isEmpty()) {

                    Toast.makeText(RegisterActivity.this, "Please enter the name", Toast.LENGTH_SHORT).show();

                } else if (pasword.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
                else if (!pasword.getText().toString().equals(confirmpass.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Password and confirmed password does not matching..", Toast.LENGTH_SHORT).show();
                } else if (phone.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter phone", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        AppDatabase.getDb(RegisterActivity.this).userDao().insertAll(new User(phone.getText().toString(), username.getText().toString()
                                , pasword.getText().toString()));

                        Toast.makeText(RegisterActivity.this, "Registeration Sucessfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, Login_Activity.class));
                        finish();

                    } catch (SQLiteConstraintException e) {
                        Toast.makeText(RegisterActivity.this, "Already Exit", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });


    }
}
