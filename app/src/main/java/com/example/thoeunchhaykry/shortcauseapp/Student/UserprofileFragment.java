package com.example.thoeunchhaykry.shortcauseapp.Student;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thoeunchhaykry.shortcauseapp.CauseItemList;
import com.example.thoeunchhaykry.shortcauseapp.MySingleton;
import com.example.thoeunchhaykry.shortcauseapp.R;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserprofileFragment extends Fragment {


    public UserprofileFragment() {
        // Required empty public constructor
    }

    Spinner sp_gender;
    ArrayList<CauseItemList> users = new ArrayList<>();
    EditText et_proFullname,et_proEmail;
    ImageView Iv_profile;
    Button btn_editprofile;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<String>splist1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_userprofile, container, false);

        et_proFullname=(EditText)view.findViewById(R.id.et_pro_fullname);
        et_proEmail=(EditText)view.findViewById(R.id.et_pro_email);
        sp_gender=(Spinner)view.findViewById(R.id.sp_gender);
        Iv_profile = (ImageView)view.findViewById(R.id.defaultprofile);
        btn_editprofile = (Button)view.findViewById(R.id.btn_editprofile);

        sp_gender = (Spinner)view.findViewById(R.id.sp_gender);
        preferences=getActivity().getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor=preferences.edit();

        final String userid = preferences.getString("userid","");
        final String fbid = preferences.getString("fbid","");

        final String[] gender = {"--Gender--","Male","Female"};
        ArrayAdapter<String> genderlist = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,gender);
        sp_gender.setAdapter(genderlist);

        String url =MySingleton.url+"userprofile.php" ;
        LoadingProfile(url,userid);

        btn_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url =MySingleton.url+"updateUserprofile.php";
                StringRequest sRequest = new StringRequest(Request.Method.POST, url
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("1")){
                            Toast.makeText(getContext(), "Successfull!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "<<< "+error.getMessage()+" >>>", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> putParams = new HashMap<String, String>();
                        putParams.put("key","isKey");
                        putParams.put("userid",userid);
                        putParams.put("fullname",et_proFullname.getText().toString());
                        putParams.put("email",et_proEmail.getText().toString());
                        putParams.put("gender",sp_gender.getSelectedItem().toString());
                        return putParams;
                    }
                };
                MySingleton.getInstance(getContext()).addToRequestQueue(sRequest);
            }
        });




        return  view;
    }

    private void  LoadingProfile(String url, final String userid){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("GetUser",response);
                        if (response!="NoData"){
                            users=new JsonConverter<CauseItemList>().toArrayList(response,CauseItemList.class);
                            CauseItemList usersList = users.get(0);
                            et_proFullname.setText(usersList.full_name);
                            et_proEmail.setText(usersList.email);
                            splist1 = new ArrayList<>();
                            if(usersList.gender!=null){
                                splist1.add(usersList.gender);

                            }else{
                                splist1.add("--Gender--");
                            }

                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,splist1);
                            sp_gender.setAdapter(adapter);
                            String path = MySingleton.path + usersList.images;
//                            if (fbid.equals("")){
//                                Picasso.with(getApplicationContext())
//                                        .load(path)
//                                        .placeholder(R.drawable.profileicon)
//                                        .error(R.drawable.defaultprofilem)
//                                        .transform(new RoundedCornersTransform())
//                                        .into(Iv_profile);
//                            }else {
//                                Picasso.with(getApplicationContext())
//                                        .load("https://graph.facebook.com/" + fbid+ "/picture?type=large")
//                                        .placeholder(R.drawable.profileicon)
//                                        .error(R.drawable.com_facebook_profile_picture_blank_portrait)
//                                        .transform(new RoundedCornersTransform())
//                                        .into(Iv_profile);
//                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"<<< " +error.getMessage()+" >>>", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> putParams = new HashMap<String, String>();
                putParams.put("key","isKey") ;
                putParams.put("userid",userid);
                return putParams;
            }
        };

        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

}
