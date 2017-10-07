package com.example.thoeunchhaykry.shortcauseapp.Student;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.thoeunchhaykry.shortcauseapp.MySingleton.url;
import static com.example.thoeunchhaykry.shortcauseapp.R.id.sp_session;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {


    public RequestFragment() {
        // Required empty public constructor
    }
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ImageView iv_setdate;
    EditText et_setdate,et_courseName,et_expPrice,et_CouresDescription;
    Button btn_request;
    LinearLayout request_layout;
    Spinner sp_causetype,sp_sesstion;
    DatePickerDialog datedialong ;

    ArrayList<CauseItemList> CaurseName;
    ArrayList<String> CaurseNameList = new ArrayList<>();
    CauseItemList itemList;
    Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        iv_setdate=(ImageView)view.findViewById(R.id.iv_setdate);
        et_setdate=(EditText)view.findViewById(R.id.et_Req_startdate);
        btn_request=(Button)view.findViewById(R.id.btn_requestcause);
        sp_causetype=(Spinner)view.findViewById(R.id.sp_causetype);
        sp_sesstion=(Spinner)view.findViewById(sp_session);
        et_CouresDescription=(EditText)view.findViewById(R.id.et_causerequestDescrip);
        et_courseName =(EditText)view.findViewById(R.id.et_Req_causename);
        et_expPrice=(EditText)view.findViewById(R.id.et_Req_expect_price);
        request_layout =(LinearLayout) view.findViewById(R.id.request_layout);

        calendar=Calendar.getInstance();

        preferences=getActivity().getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor=preferences.edit();
        final String user_id = preferences.getString("userid","");


        String urlCourseType = url+"coursetypelist.php";
        LoadingCourse(urlCourseType,sp_causetype,"--Select Course Type--");

        final String[] allsesstion = {"--Session--","Morning","Affternoon","Evening","Weekend"};
        ArrayAdapter<String> listsesstion = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,allsesstion);
        sp_sesstion.setAdapter(listsesstion);
        iv_setdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datedialong = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Calendar getcalendar = Calendar.getInstance();
                        getcalendar.set(Calendar.YEAR,year);
                        getcalendar.set(Calendar.MONTH,month);
                        getcalendar.set(Calendar.DAY_OF_MONTH,day);
                       // String thisdate = DateUtils.formatDateTime(getActivity(),getcalendar.getTimeInMillis(),DateUtils.FORMAT_SHOW_DATE );
                        String monthly =month+1+"";
                        if (month+1 <10){
                            monthly="0"+(month+1);
                        }

                        String thisdate = day+"-"+monthly+"-"+year;

                        et_setdate.setText(thisdate);

                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

                datedialong.show();
            }
        });

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String sp_coursetypeId = sp_causetype.getSelectedItemPosition()+"";
                final String sp_getsession = sp_sesstion.getSelectedItem().toString();

                String url = MySingleton.url+"requestCourse.php";

                StringRequest stringRequests = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("LogRespo","<<<"+response+">>>");
                                if (response.equals("1")){
                                    Toast.makeText(getContext(), "Successfully !", Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.d("LogRespo>>>", response);
                                    Toast.makeText(getContext(), "Not Succesfully !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error Request", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> putParam = new HashMap<String, String>();
                        putParam.put("key","kry@");
                        putParam.put("user_id",user_id);
                        putParam.put("courseName",et_courseName.getText().toString());
                        putParam.put("setDate",et_setdate.getText().toString());
                        putParam.put("coursetype",sp_coursetypeId);
                        putParam.put("session",sp_getsession);
                        putParam.put("expPrice",et_expPrice.getText().toString());
                        putParam.put("CourseDescr",et_CouresDescription.getText().toString());
                        return putParam;
                    }
                };
                MySingleton.getInstance(getContext()).addToRequestQueue(stringRequests);

                Log.d("get",et_setdate.getText().toString());
            }
        });

        return view;

    }
    private void LoadingCourse(String url, final Spinner spinner, final String String){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ReSppone",response);
                        CaurseName = new JsonConverter<CauseItemList>().toArrayList(response,CauseItemList.class);
                        CaurseNameList.add(String);
                        for (int i = 0; i < CaurseName.size(); i++) {
                            itemList = CaurseName.get(i);
                            CaurseNameList.add(itemList.CourseName);
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, CaurseNameList);
                        spinner.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


}
