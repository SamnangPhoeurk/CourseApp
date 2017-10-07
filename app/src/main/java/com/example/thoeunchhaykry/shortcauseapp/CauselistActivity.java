package com.example.thoeunchhaykry.shortcauseapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thoeunchhaykry.shortcauseapp.Student.StudentMainActivity;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CauselistActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView rv_causelist;
    String csgetid="";
    String userid;
    String url;
    ArrayList<CauseItemList> causelist= new ArrayList<>();
    CauseItemAdapter adapter;
    LinearLayout request_block,notag_block;
    ArrayList<CauseItemList> newCauselist;
    String data;
    private RelativeLayout relative;
    String TAG = getClass().getName();
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_causelist);


        getSupportActionBar().setTitle("Default");
        request_block=(LinearLayout)findViewById(R.id.requset_block);
        notag_block=(LinearLayout)findViewById(R.id.notag_block);
        relative=(RelativeLayout)findViewById(R.id.activity_causelist);
        request_block.setVisibility(View.INVISIBLE);
        request_block.setVisibility(View.GONE);
        notag_block.setVisibility(View.GONE);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swiperefresh);

        rv_causelist=(RecyclerView)findViewById(R.id.rv_causelist);
        rv_causelist.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rv_causelist.setLayoutManager(manager);

        preferences=getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor=preferences.edit();
        final String email_checking = preferences.getString("email","");
        final String password_checking = preferences.getString("password","");
        final String fbid = preferences.getString("fbid","");
        userid = preferences.getString("userid","");
        Log.d(TAG,"userid: "+userid);

        final String url1 =MySingleton. url+"login.php";

        Intent cgetid = getIntent();
        csgetid= cgetid.getStringExtra("coursetypeid");
        if (csgetid.equals("1")){
            getSupportActionBar().setTitle("Networking");
        }else if (csgetid.equals("2")){
            getSupportActionBar().setTitle("Designing");
        }else if (csgetid.equals("3")){
            getSupportActionBar().setTitle("Programming");
        }else if (csgetid.equals("4")){
            getSupportActionBar().setTitle("Multimedia");
        }else if (csgetid.equals("5")){
            getSupportActionBar().setTitle("Computer Repair");
        }else if (csgetid.equals("6")){
            getSupportActionBar().setTitle("Microsoft Offices");
        }else if (csgetid.contains("favorite")){
            getSupportActionBar().setTitle("Favorite");
        }else {
            Toast.makeText(this, "No layout", Toast.LENGTH_SHORT).show();
        }


        request_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!(email_checking.equals("") && password_checking.equals(""))) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("true")){
                                    Intent in = new Intent(getApplicationContext(),StudentMainActivity.class);
                                    in.putExtra("requestsesstion","1");
                                    startActivity(in);
                                    finish();
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                }else {
                                    Intent in = new Intent(getApplicationContext(),LoginActivity.class);
                                    in.putExtra("requestsesstion","0");
                                    startActivity(in);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("secret","123");
                                params.put("email",email_checking);
                                params.put("password",password_checking);
                                return params;
                            }
                        };
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                }else if (!(fbid.equals(""))){
                    Intent in = new Intent(getApplicationContext(),StudentMainActivity.class);
                    in.putExtra("requestsesstion","1");
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();
                }else {
                    Intent in = new Intent(getApplicationContext(),LoginActivity.class);
                    in.putExtra("requestsesstion","0");
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        });

//  ====== check url to query data ======

        if (!(email_checking.equals(""))|| !(fbid.equals(""))){
            if (csgetid.contains("favorite")){
                url = MySingleton.url+"favoritelist.php";
            }else if (csgetid.equals("My Course")) {

                url =MySingleton.url+"postshortcourselist.php";
            }else {
                url = MySingleton.url+"shortcourselist.php";
            }
        }else {
            url = MySingleton.url+"shortcourselist.php";
        }

        loadingdata(url);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingdata(url);
            }
        });

        Log.d("url: ",url);
        Log.d("url >",email_checking + ">>" + fbid);

    }
    private void loadingdata(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("reponse","this is reponse:"+response);
                        if (response.contains("nodata")) {
                            request_block.setVisibility(View.VISIBLE);
                            data = "0";
                        }else if(response.contains("nofavorite")){
                            notag_block.setVisibility(View.VISIBLE);
                            data = "0";
                        }else {
                            data="1";
                            causelist = new JsonConverter<CauseItemList>().toArrayList(response,CauseItemList.class);
                            adapter = new CauseItemAdapter(getApplicationContext(),causelist);
                            rv_causelist.setAdapter(adapter);
                        }
                        Log.d(TAG,"favorite: " +response);

                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    Log.d("error",error.getMessage()+"");
                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("courseid",csgetid);
                params.put("userid",userid);

                return params;


            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        if (data=="0"){
            Snackbar snackbar= Snackbar
                    .make(relative, "No data to serch !", Snackbar.LENGTH_LONG)
                    .setAction("Action", null);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(20);
            snackbar.show();

        }else {
            newCauselist = new ArrayList<>();
            for (CauseItemList causeItemLists : causelist) {
                String titlecause = causeItemLists.pc_title.toLowerCase();
        //            String test = causeItemLists.causeName.toLowerCase();
                if (titlecause.contains(newText)) {
                    newCauselist.add(causeItemLists);
                }
            }
            adapter.setFilter(newCauselist);
        }



        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            // ...

            // up button
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
