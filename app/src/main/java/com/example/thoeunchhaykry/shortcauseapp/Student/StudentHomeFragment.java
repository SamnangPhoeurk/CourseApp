package com.example.thoeunchhaykry.shortcauseapp.Student;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.thoeunchhaykry.shortcauseapp.CauselistActivity;
import com.example.thoeunchhaykry.shortcauseapp.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentHomeFragment extends Fragment {


    public StudentHomeFragment() {
        // Required empty public constructor
    }
    LinearLayout network_layout,desing_layout,program_layout,multimedia_layout,com_repair,ms_office,langluage_layout,towDthreeD_layout,business_layout,economic_layout,law_layout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_home, container, false);
        network_layout=(LinearLayout)view.findViewById(R.id.network_layout);
        desing_layout=(LinearLayout)view.findViewById(R.id.design_layout);
        program_layout=(LinearLayout)view.findViewById(R.id.program_layout);
        multimedia_layout=(LinearLayout)view.findViewById(R.id.multimedia_layout);
        langluage_layout=(LinearLayout)view.findViewById(R.id.langluage_layout);
        towDthreeD_layout=(LinearLayout)view.findViewById(R.id.towDthreeD_layout);
        com_repair=(LinearLayout)view.findViewById(R.id.repair_layout);
        ms_office=(LinearLayout)view.findViewById(R.id.three_block);
        business_layout=(LinearLayout)view.findViewById(R.id.business_layout);
        economic_layout=(LinearLayout)view.findViewById(R.id.economic_layout);
        law_layout=(LinearLayout)view.findViewById(R.id.law_layout);

        network_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","1");
                startActivity(in);
               getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });


        desing_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","2");
                startActivity(in);
                getActivity(). overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        program_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","3");
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        multimedia_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","4");
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        com_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","5");
                startActivity(in);
                getActivity(). overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        ms_office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","6");
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        towDthreeD_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","7");
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        business_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","8");
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        economic_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","9");
                startActivity(in);
                getActivity(). overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        langluage_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","10");
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        law_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                in.putExtra("coursetypeid","11");
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });


        return view;
    }

}
