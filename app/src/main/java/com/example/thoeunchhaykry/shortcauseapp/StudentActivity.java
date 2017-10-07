package com.example.thoeunchhaykry.shortcauseapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;

public class StudentActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button btn_logout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        btn_logout=(Button)findViewById(R.id.btn_logout);

        pref=getSharedPreferences("login.conf", Context.MODE_PRIVATE);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottonnavigation);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor=pref.edit();
                editor.clear();
                editor.commit();
                Intent in = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                LoginManager.getInstance().logOut();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.homeitem){

                }else if (item.getItemId()==R.id.searchitem){

                }else if (item.getItemId()==R.id.requestitem){
                    Toast.makeText(getApplicationContext(),"request item", Toast.LENGTH_SHORT).show();
                }else if (item.getItemId()==R.id.alertitem){

                }else if (item.getItemId()==R.id.settingitem){

                }

                return true;
            }
        });
    }
}
