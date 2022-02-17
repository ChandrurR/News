package com.example.news_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.news_app.util.AppPreferences;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView userName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        navigationView = findViewById(R.id.navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_tool_bar);
        setSupportActionBar(toolbar);
        ImageView humber = toolbar.findViewById(R.id.humburger);
        humber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        drawerLayout = findViewById(R.id.drawer);
        userName = navigationView.getHeaderView(0).findViewById(R.id.textView2);
        String name = new AppPreferences(MainActivity.this).getValue(AppPreferences.IS_USER_LOGGED);
        userName.setText(name);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() ==  R.id.state)
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    loadFragment(R.id.frame,new ViewPagerFragment());
                }
                //Maps

                if(item.getItemId() ==R.id.maps)
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    loadFragment(R.id.frame,new MapFragment());
                }

                if(item.getItemId() == R.id.logout){
                    new AppPreferences(MainActivity.this).setValue(AppPreferences.IS_USER_LOGGED,""); //empty to clear
                    startActivity(new Intent(MainActivity.this,Login_Activity.class));
                    finish();
                }
                return false;
            }
        });
        loadHome("India");
    }

    public void loadHome(String key){
        StateWiseNews fra= new StateWiseNews();
        Bundle b=new Bundle();
        b.putString("key",key);
        fra.setArguments(b);
        loadFragment(R.id.frame,fra);
    }


    /// fragment loading
    public void loadFragment(int id, Fragment fragment) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         if (item.getItemId()==R.id.search){
                   alert();
         }
         return false;
    }

    public void alert(){
        EditText inputEditTextField= new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search for news")
                .setView(inputEditTextField)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String editTextInput = inputEditTextField.getText().toString();
                        loadHome(editTextInput);
                    }
                });


        builder.create().show();
    }
}
