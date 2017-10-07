package com.example.thoeunchhaykry.shortcauseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.thoeunchhaykry.shortcauseapp.FragmentCauseType.DesignFragment;
import com.example.thoeunchhaykry.shortcauseapp.FragmentCauseType.NetwordFragment;
import com.example.thoeunchhaykry.shortcauseapp.FragmentCauseType.ProgramFragment;

public class ControllActivity extends AppCompatActivity {

    String layout_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controll);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent mylayout = getIntent();
        layout_id=mylayout.getStringExtra("layoutname");

        if (layout_id.equals("1")){
            getSupportActionBar().setTitle("Network");
            NetwordFragment networdFragment = new NetwordFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.controll_layout,networdFragment)
                    .commit();
        }else if (layout_id.equals("2")){
            getSupportActionBar().setTitle("Design");
            DesignFragment designFragment = new DesignFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.controll_layout,designFragment)
                    .commit();
        }else if (layout_id.equals("3")){
            getSupportActionBar().setTitle("Programming");
            ProgramFragment programFragment = new ProgramFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.controll_layout,programFragment)
                    .commit();
        }

    }
}
