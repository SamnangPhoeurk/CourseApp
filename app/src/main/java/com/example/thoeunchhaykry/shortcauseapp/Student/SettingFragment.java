package com.example.thoeunchhaykry.shortcauseapp.Student;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thoeunchhaykry.shortcauseapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView tv_logout,tv_changePw,tv_changeEmail;
    EditText et_oldPassword,et_newPassword,et_conPassword,et_oldEmail,et_newEmail,et_PasswordEmail;
    Button btn_cancell,btn_change,btn_Echange,btn_Ecancel;
    LinearLayout linea_changePw,linea_changeEmail;
    public static int showHide = 0;
    public static int EshowHide = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        tv_logout=(TextView)view.findViewById(R.id.tv_logOut);
        tv_changeEmail=(TextView)view.findViewById(R.id.tv_changeEmail);
        tv_changePw=(TextView)view.findViewById(R.id.tv_ChangesPW);
        linea_changePw=(LinearLayout)view.findViewById(R.id.linear_ChangePass);
        linea_changeEmail=(LinearLayout)view.findViewById(R.id.linear_ChangeEmail);
        btn_Ecancel=(Button)view.findViewById(R.id.btn_Ecancel);
        btn_Echange=(Button)view.findViewById(R.id.btn_Echanges);
        btn_cancell=(Button)view.findViewById(R.id.btn_cancel);
        btn_change=(Button)view.findViewById(R.id.btn_changes);
        et_oldPassword=(EditText)view.findViewById(R.id.et_oldpassword);
        et_newPassword=(EditText)view.findViewById(R.id.et_NewPassword);
        et_conPassword=(EditText)view.findViewById(R.id.et_conNewPassword);
        et_oldEmail=(EditText)view.findViewById(R.id.et_oldEmail);
        et_newEmail=(EditText)view.findViewById(R.id.et_NewEmail);
        et_PasswordEmail=(EditText)view.findViewById(R.id.et_Emailpassword);


        tv_changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showHide==0){
                    linea_changePw.setVisibility(view.VISIBLE);
                    showHide=1;
                }else {
                    linea_changePw.setVisibility(view.GONE);
                    showHide=0;
                }
            }
        });

        tv_changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (EshowHide==0){
                    linea_changeEmail.setVisibility(view.VISIBLE);
                    EshowHide=1;
                }else {
                    linea_changeEmail.setVisibility(view.GONE);
                    EshowHide=0;
                }
            }
        });



        return view;
    }

}
