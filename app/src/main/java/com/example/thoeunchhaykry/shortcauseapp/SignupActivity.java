package com.example.thoeunchhaykry.shortcauseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.kosalgeek.android.md5simply.MD5;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    Spinner sp_gender;
    EditText et_name,et_email,et_address,et_password,et_confirmpass,et_phone,et_company;
    private  static RadioGroup radio_gruop;
    private static RadioButton radio_button;
    RadioButton rb_student,rb_organization;
    ImageView iv_profilepic;
    Button register_btn;
    LinearLayout linear_company,linear_student,sp_linear_gender;
    String logintype;
    String gender;
    String userid="";
    String[] genderlist;
    ImageView backsignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sp_gender=(Spinner)findViewById(R.id.sp_gender);
        et_name=(EditText)findViewById(R.id.et_fullname);
        et_address=(EditText)findViewById(R.id.et_address);
        et_phone=(EditText)findViewById(R.id.et_phone);
        et_email=(EditText)findViewById(R.id.et_sign_email);
        et_password=(EditText)findViewById(R.id.et_signup_password);
        et_company=(EditText)findViewById(R.id.et_company);
        et_confirmpass=(EditText)findViewById(R.id.et_confirmpass);
        iv_profilepic=(ImageView)findViewById(R.id.iv_profilepic);
        radio_gruop=(RadioGroup)findViewById(R.id.rg_youarea);
        backsignup=(ImageView)findViewById(R.id.backsignup);
  //      rb_student=(RadioButton)findViewById(R.id.rb_student);
//        rb_organization=(RadioButton)findViewById(R.id.rb_organization);
        register_btn=(Button)findViewById(R.id.register_btn);
        linear_company=(LinearLayout)findViewById(R.id.linear_company);
        linear_student=(LinearLayout)findViewById(R.id.linear_student);
        sp_linear_gender=(LinearLayout)findViewById(R.id.sp_liner_gender);
        linear_company.setVisibility(View.INVISIBLE);
        linear_company.setVisibility(View.GONE);
        linear_student.setVisibility(View.INVISIBLE);
        linear_student.setVisibility(View.GONE);
        sp_linear_gender.setVisibility(View.INVISIBLE);
        sp_linear_gender.setVisibility(View.GONE);

        final AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        awesomeValidation.setContext(this);
        awesomeValidation.addValidation(this,R.id.et_fullname,"[a-zA-Z\\s]+",R.string.error_name);
        awesomeValidation.addValidation(this,R.id.sp_gender,"[a-zA-Z\\s]+",R.string.error_gender);
        awesomeValidation.addValidation(this,R.id.et_sign_email, Patterns.EMAIL_ADDRESS,R.string.error_email);
        awesomeValidation.addValidation(this,R.id.et_confirmpass,R.id.et_signup_password,R.string.error_confirmpass);

        Intent getintent = getIntent();

        backsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        logintype=getintent.getStringExtra("logintype");
        Log.d("logintype",logintype);
            if (logintype.equals("1")){
                et_name.setText(getintent.getStringExtra("name"));
                userid=getintent.getStringExtra("idFacebook");
                et_email.setText(getintent.getStringExtra("email"));
    //            sp_gender.setTextAlignment(Integer.parseInt(getintent.getStringExtra("gender")));

               genderlist = new String[]{getintent.getStringExtra("gender")};
                Picasso.with(getApplicationContext())
                        .load("https://graph.facebook.com/" + userid+ "/picture?type=large")
                        .placeholder(R.drawable.profileicon)
                        .error(R.drawable.com_facebook_profile_picture_blank_portrait)
                        .transform(new RoundedCornersTransform())
                        .into(iv_profilepic);
            }else {
                Toast.makeText(getApplicationContext(),"Login With email", Toast.LENGTH_SHORT).show();
                genderlist = new String[]{"--Gender--", "Male", "Female"};

            }
        ArrayAdapter<String> genderadapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,genderlist);
        sp_gender.setAdapter(genderadapter);
        gender = sp_gender.getSelectedItem().toString();

            register_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selected_id = radio_gruop.getCheckedRadioButtonId();
                    radio_button=(RadioButton)findViewById(selected_id);
                    if (radio_button==null){
                        Toast.makeText(getApplicationContext(), "Select radio button", Toast.LENGTH_SHORT).show();
                    }else {
                     if (et_password!=null){
                         if (awesomeValidation.validate()){

                             String url =MySingleton.url+"signup.php";
//                             String url ="http://10.0.3.2/shortcauseapp/signup.php";
                             StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                     url,
                                     new Response.Listener<String>() {
                                         @Override
                                         public void onResponse(String response) {
                                             Log.d("abc",response);
                                            if (response.contains("true")){
                                                Intent in = new Intent(getApplicationContext(),LoginActivity.class);
                                                startActivity(in);
                                                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                                            }else if (response.contains("exist")){
                                                Toast.makeText(getApplicationContext(), "Your eamil is exited.", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                                            }

                                         }
                                     }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError error) {
                                     Log.d("error",error.getMessage());

                                 }
                             })
                             {
                                 @Override
                                 protected Map<String, String> getParams() throws AuthFailureError {
                                     Map<String,String> params = new HashMap<String, String>();
                                     params.put("secret","123");
                                     params.put("fullname",et_name.getText().toString());
                                     params.put("gender",gender);
                                     params.put("address",et_address.getText().toString());
                                     params.put("email",et_email.getText().toString());
                                     params.put("company",et_company.getText().toString());
                                     params.put("phonenum",et_phone.getText().toString());
                                     params.put("password", MD5.encrypt(et_password.getText().toString()));
                                     params.put("facebookid",userid);
                                     params.put("usertype",radio_button.getText().toString());

                                     // params.put("","");
                                     return params;
                                 }
                             };
                             MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                         }else {
                             //Toast.makeText(getApplicationContext(), "Please enter your password!", Toast.LENGTH_SHORT).show();
                         }
                     }
                    }
                }
            });
    }

    public   void Slectedusertype(View view){
        boolean checked =((RadioButton)view).isChecked();

        switch (view.getId()){
            case R.id.rb_student:
                if (checked){
                    if (logintype.equals("1")){
                        linear_company.setVisibility(View.INVISIBLE);
                        linear_company.setVisibility(View.GONE);
                        linear_student.setVisibility(View.INVISIBLE);
                        linear_student.setVisibility(View.GONE);
                        sp_linear_gender.setVisibility(View.INVISIBLE);
                        sp_linear_gender.setVisibility(View.GONE);
                    }else {
                        linear_student.setVisibility(View.VISIBLE);
                        sp_linear_gender.setVisibility(View.VISIBLE);
                        linear_company.setVisibility(View.INVISIBLE);
                        linear_company.setVisibility(View.GONE);

                    }
                }
                break;
            case R.id.rb_organization:
                if (checked){
                    if (logintype.equals("1")){
                        linear_company.setVisibility(View.INVISIBLE);
                        linear_company.setVisibility(View.GONE);
                        linear_student.setVisibility(View.INVISIBLE);
                        linear_student.setVisibility(View.GONE);
                        sp_linear_gender.setVisibility(View.INVISIBLE);
                        sp_linear_gender.setVisibility(View.GONE);
                    }else {
                        linear_company.setVisibility(View.VISIBLE);
                        linear_student.setVisibility(View.VISIBLE);
                        sp_linear_gender.setVisibility(View.INVISIBLE);
                        sp_linear_gender.setVisibility(View.GONE);

                    }
                }
                break;
        }
    }

}
