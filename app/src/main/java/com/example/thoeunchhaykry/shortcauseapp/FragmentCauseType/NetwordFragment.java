package com.example.thoeunchhaykry.shortcauseapp.FragmentCauseType;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.thoeunchhaykry.shortcauseapp.CauselistActivity;
import com.example.thoeunchhaykry.shortcauseapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NetwordFragment extends Fragment {


    public NetwordFragment() {
        // Required empty public constructor
    }

LinearLayout cisco_network;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_netword, container, false);
        cisco_network=(LinearLayout)view.findViewById(R.id.cisco_network);
        cisco_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getContext(),CauselistActivity.class);
                startActivity(in);
            }
        });
        return view;

    }

}
