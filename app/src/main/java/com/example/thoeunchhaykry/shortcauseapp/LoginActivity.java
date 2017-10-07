package com.example.thoeunchhaykry.shortcauseapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thoeunchhaykry.shortcauseapp.Student.StudentMainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    EditText email;
    EditText password;
    TextView tv_forgetpassword,tv_signup;
    ImageView backnavigation;

    LoginButton fb_loginButton;
    CallbackManager callbackManager;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String TAG = getClass().getName();
    String url;
    LinearLayout linear_layout;
    public static int userID;
    public static int user_type;
    ArrayList<CauseItemList> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);

        email=(EditText)findViewById(R.id.et_email);
        password=(EditText)findViewById(R.id.et_password);
        tv_forgetpassword=(TextView)findViewById(R.id.tv_forgetpassword);
        tv_signup=(TextView)findViewById(R.id.tv_signup);
        tv_forgetpassword.setText(Html.fromHtml("<u>forget password?</u>"));
        tv_signup.setText(Html.fromHtml("<u>Sign Up</u>"));

        backnavigation=(ImageView)findViewById(R.id.backnavigation);
        login_btn=(Button)findViewById(R.id.login_btn);
        fb_loginButton=(LoginButton) findViewById(R.id.fb_login_btn);
        linear_layout=(LinearLayout)findViewById(R.id.activity_login);

        callbackManager = CallbackManager.Factory.create();

        fb_loginButton.setReadPermissions("user_friends");
        fb_loginButton.setReadPermissions("public_profile");
        fb_loginButton.setReadPermissions("email");
        fb_loginButton.setReadPermissions("user_birthday");
        fb_loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));


        preferences=getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor=preferences.edit();


        tv_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,tv_signup.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),SignupActivity.class);
                in.putExtra("logintype","2");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        backnavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
//====== Check user session on loading =====
        final String email_checking = preferences.getString("email","");
        final String password_checking = preferences.getString("password","");
        String fbid = preferences.getString("fbid","");
        final String emailLog = email.getText().toString();
        final String passLog = MD5.encrypt(password.getText().toString());

        url =MySingleton.url+"login.php";

        if(!(email_checking.equals("") && password_checking.equals(""))){

            Loginchecking(email_checking,password_checking,url);
    //==== checking user session with fb ====

        }else if (!(fbid.equals(""))){
            Intent in = new Intent(getApplicationContext(),StudentMainActivity.class);
            in.putExtra("requestsesstion","0");
            startActivity(in);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            finish();
        }
 //  === end chceking ====

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailLog = email.getText().toString();
                final String passLog = MD5.encrypt(password.getText().toString());
                Loginchecking(emailLog,passLog,url);

            }
        });



        fb_loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity>", response.toString());
                        // Get facebook data from login

                        try {

                            String email = object.getString("email");
                           // String birthday = object.getString("birthday");
                            String name = object.getString("name");
                            String gender = object.getString("gender");
                            String userid = object.getString("id");

                            Intent in = new Intent(getApplicationContext(),SignupActivity.class);
                            in.putExtra("name", name);
                            in.putExtra("idFacebook",userid);

                            Log.d("fbid",userid);

                            in.putExtra("email", email);
                            in.putExtra("gender",gender);
                            in.putExtra("logintype","1");

                     //==== save session user with fb ===
                            editor.putString("fbid",userid);
                            editor.apply();
                            startActivity(in);
                            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login is Cancel!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Error",error.getMessage());
                Toast.makeText(getApplicationContext(), "Login is error !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch (id){
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
    private void Loginchecking(final String email, final String password,String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ResponONE",">>"+response);
                if (response.equals("false")){

                    Snackbar snackbar= Snackbar
                            .make(linear_layout, "Please Check your email and password again One !", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(15);

                    snackbar.show();

                }else if(response.equals("string")){
                    Snackbar snackbar= Snackbar
                            .make(linear_layout, "Please Check your email and password again !", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(15);

                    snackbar.show();
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
                Log.d("Error",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>() ;
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
