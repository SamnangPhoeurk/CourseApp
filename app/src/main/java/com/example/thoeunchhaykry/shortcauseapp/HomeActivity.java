package com.example.thoeunchhaykry.shortcauseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    LinearLayout network_layout,desing_layout,program_layout,multimedia_layout,com_repair,ms_office,langluage_layout,towDthreeD_layout,business_layout,economic_layout,law_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MultiDex.install(this);


        network_layout=(LinearLayout)findViewById(R.id.network_layout);
        desing_layout=(LinearLayout)findViewById(R.id.design_layout);
        program_layout=(LinearLayout)findViewById(R.id.program_layout);
        multimedia_layout=(LinearLayout)findViewById(R.id.multimedia_layout);
        langluage_layout=(LinearLayout)findViewById(R.id.langluage_layout);
        towDthreeD_layout=(LinearLayout)findViewById(R.id.towDthreeD_layout);
        com_repair=(LinearLayout)findViewById(R.id.repair_layout);
        ms_office=(LinearLayout)findViewById(R.id.three_block);
        business_layout=(LinearLayout)findViewById(R.id.business_layout);
        economic_layout=(LinearLayout)findViewById(R.id.economic_layout);
        law_layout=(LinearLayout)findViewById(R.id.law_layout);

        network_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Hello Network Lahyout", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","1");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        desing_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","2");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        program_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","3");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        multimedia_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","4");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        com_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","5");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        ms_office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","6");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        towDthreeD_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","7");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        business_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","8");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        economic_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","9");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        langluage_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","10");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        law_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","11");
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_login:
                Intent in = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
