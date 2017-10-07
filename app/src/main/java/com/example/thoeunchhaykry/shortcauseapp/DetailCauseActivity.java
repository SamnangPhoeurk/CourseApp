package com.example.thoeunchhaykry.shortcauseapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DetailCauseActivity extends AppCompatActivity {

    TextView tv_detail_name,tv_detail_startdate,tv_detail_enddate,
            tv_detail_price,tv_detail_description,tv_detail_contact,
            tv_detail_address,tv_detail_postdate;
    ImageView iv_detail_image,Ivd_share,Ivd_tag,Ivd_call;
    int getstatus=0;
    LinearLayout linea_call;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cause);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_detail_name=(TextView)findViewById(R.id.tv_detail_name);
        tv_detail_enddate=(TextView)findViewById(R.id.tv_detail_enddate);
        tv_detail_startdate=(TextView)findViewById(R.id.tv_detail_startdate);
        tv_detail_price=(TextView)findViewById(R.id.tv_detail_price);
        tv_detail_description=(TextView)findViewById(R.id.tv_detail_description);
        tv_detail_contact=(TextView)findViewById(R.id.tv_detail_contact);
        tv_detail_address=(TextView)findViewById(R.id.tv_detail_address);
        tv_detail_postdate=(TextView)findViewById(R.id.tv_detail_postdate);
        iv_detail_image=(ImageView)findViewById(R.id.tv_detail_image);
        Ivd_share=(ImageView)findViewById(R.id.Ivd_share);
        Ivd_tag=(ImageView)findViewById(R.id.Ivd_tag);
        Ivd_call=(ImageView)findViewById(R.id.ivDe_call);
        linea_call = (LinearLayout)findViewById(R.id.linear_call);


        preferences=getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor=preferences.edit();
        final String userid = preferences.getString("userid","");
        final String getemail=preferences.getString("email","");
        final String getpassword=preferences.getString("password","");


//        Intent in = getIntent();
        if (getIntent().getSerializableExtra("itemcauselist") !=null){
            final CauseItemList getcauselist = (CauseItemList)getIntent().getSerializableExtra("itemcauselist");
            tv_detail_name.setText(getcauselist.pc_title);
            tv_detail_startdate.setText("Start Date: " +getcauselist.startdate);
            tv_detail_enddate.setText("End Date: "+getcauselist.enddate);
            tv_detail_price.setText("Price: "+getcauselist.price+" $");
            tv_detail_description.setText(getcauselist.pc_descriptions);
            tv_detail_contact.setText("Contact to: "+getcauselist.full_name);
            tv_detail_address.setText(getcauselist.address);
            String poston = getcauselist.datepost.substring(0,10);
            tv_detail_postdate.setText("Post On: "+poston);
           getstatus = getcauselist.status;

            if (getstatus==0){
                Ivd_tag.setImageResource(R.mipmap.tag_icons);
            }else {
                Ivd_tag.setImageResource(R.mipmap.tagpink_icon);
            }
            String path=MySingleton.path+getcauselist.pc_image;
            Picasso.with(getApplicationContext())
                    .load(path)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.noimages)
                    .into(iv_detail_image);


            linea_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+getcauselist.phone));

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }else {
                        Log.d("callclick","Not Permission"+getcauselist.phone);
                    }
                    startActivity(callIntent);
                    Log.d("callclick","Click Linear lahyot"+getcauselist.phone);
                }
            });


            //Share this cause to social network

            Ivd_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareintent = new Intent();
                    shareintent.setAction(Intent.ACTION_SEND );
                    shareintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    shareintent.setType("text/plain");
                    String sharebody = "your body here";
                    String sharesub = "your Subject here";
                    shareintent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                    shareintent.putExtra(Intent.EXTRA_TEXT,sharebody);
                    Intent chooserIntent = Intent.createChooser(shareintent,"Share using");
                    chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(chooserIntent);
                }
            });


            // check and update favorite cause
            Ivd_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!(getemail.equals("")&&getpassword.equals(""))){
                        if (getstatus==0){
                            Ivd_tag.setImageResource(R.mipmap.tagpink_icon);
                            getstatus=1;
                        }else {
                            Ivd_tag.setImageResource(R.mipmap.tag_icons);
                            getstatus=0;
                        }
                        Toast.makeText(getApplicationContext(), getstatus+"", Toast.LENGTH_SHORT).show();
                        String url = MySingleton.url+"favorite.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("repon: ",response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("userid",userid);
                                params.put("pc_id",getcauselist.pc_id+"");
                                params.put("status",getstatus+"");
                                return params;
                            }
                        };
                        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                    }else {
                        Intent in = new Intent(getApplicationContext(),LoginActivity.class);
//                    in.putExtra("requestsesstion","0");
                        startActivity(in);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }

                }
            });

            // end check and update favorite

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
}
