package com.example.thoeunchhaykry.shortcauseapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class SplahScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIMEOUT=4000;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String TAG = getClass().getName();
    ArrayList<CauseItemList> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah_screen);

        preferences=getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor=preferences.edit();
        final String TAG = getClass().getName();
        final String email_checking = preferences.getString("email","");
        final String password_checking = preferences.getString("password","");

        final String fbid = preferences.getString("fbid","");
        final String url =MySingleton.url+"login.php";
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!(email_checking.equals("") && password_checking.equals(""))){

                    Loginchecking(email_checking,password_checking,url);
                    //==== checking user sesstion with fb ====

                }else if (!(fbid.equals(""))){
                    Intent in = new Intent(getApplicationContext(),StudentMainActivity.class);
                    in.putExtra("requestsesstion","0");
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();
                }else {
                    Intent in = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(in);
                    finish();
                }

            }
        },SPLASH_TIMEOUT);
    }

    private void Loginchecking(final String email, final String password,String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("false")){

                    Toast.makeText(getApplicationContext(), "Please Login again!", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(in);
                    finish();
                    Log.d(TAG,"getrespont: "+response);

                }else if(response.equals("string")){
                    Toast.makeText(getApplicationContext(), "Please Login again!", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(in);
                    finish();
                    Log.d(TAG,"getrespont: "+response);
                }else {
                    users=new JsonConverter<CauseItemList>().toArrayList(response,CauseItemList.class);
                    CauseItemList usersList = users.get(0);
                    Log.d("UserList",usersList. user_type+"");

                    if(email.equals(usersList.email)&&password.equals(usersList.password)){
                        //=====save user sesstion on success to login===
                        editor.putString("userid",usersList.user_id+"");
                        editor.putString("user_type",usersList.user_type+"");
                        editor.putString("email",usersList.email);
                        editor.putString("password", usersList.password);
                        editor.apply();


                        Intent in = new Intent(getApplicationContext(),StudentMainActivity.class);
                        in.putExtra("requestsesstion","0");
                        //in.putExtra("user_type",usersList.user_type);
                        startActivity(in);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        finish();
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.getMessage()+"");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("secret","123");
                params.put("email",email);
                params.put("password",password);
                Log.d(TAG,"password : "+password);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}
