package com.example.thoeunchhaykry.shortcauseapp.Organization;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thoeunchhaykry.shortcauseapp.CauseItemList;
import com.example.thoeunchhaykry.shortcauseapp.MySingleton;
import com.example.thoeunchhaykry.shortcauseapp.R;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

public class RequestCourseActivity extends AppCompatActivity {

    RecyclerView rvReqCourse;

    String userid;
    ArrayList<CauseItemList> requestCourse= new ArrayList<>();
    RequestCourseAdapter adapter;
    String TAG = getClass().getName();
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_course);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.SwipeRefreshRequestCourse);

        rvReqCourse = (RecyclerView)findViewById(R.id.rv_requestCourse);
        rvReqCourse.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rvReqCourse.setLayoutManager(manager);

        preferences=getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor=preferences.edit();
        final String email_checking = preferences.getString("email","");
        final String password_checking = preferences.getString("password","");
        final String fbid = preferences.getString("fbid","");
        userid = preferences.getString("userid","");
        Log.d(TAG,"userid: "+userid);

        String url = MySingleton.url+"requestedcourse.php";
        loadingdata(url);

    }

    private void loadingdata(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("reponse","this is reponse:"+response);
                        if (response.contains("nodata")) {
                            Toast.makeText(RequestCourseActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                        }else {
                            requestCourse = new JsonConverter<CauseItemList>().toArrayList(response,CauseItemList.class);
                            adapter = new RequestCourseAdapter(getApplicationContext(),requestCourse);
                            rvReqCourse.setAdapter(adapter);
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
                });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
