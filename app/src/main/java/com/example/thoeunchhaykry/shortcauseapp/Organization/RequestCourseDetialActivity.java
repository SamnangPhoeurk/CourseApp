package com.example.thoeunchhaykry.shortcauseapp.Organization;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thoeunchhaykry.shortcauseapp.CauseItemList;
import com.example.thoeunchhaykry.shortcauseapp.MySingleton;
import com.example.thoeunchhaykry.shortcauseapp.R;
import com.squareup.picasso.Picasso;

import static com.example.thoeunchhaykry.shortcauseapp.R.id.tv_detail_description;
import static com.example.thoeunchhaykry.shortcauseapp.R.id.tv_detail_name;
import static com.example.thoeunchhaykry.shortcauseapp.R.id.tv_detail_price;
import static com.example.thoeunchhaykry.shortcauseapp.R.id.tv_detail_startdate;

public class RequestCourseDetialActivity extends AppCompatActivity {

    ImageView ivUserImage;
    TextView tvName, tvEmail, tvPhone, tvCourseName, tvCourseType, tvStartDate, tvExpPrice, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_course_detial);
        ivUserImage = (ImageView) findViewById(R.id.tv_detail_image);
        tvName = (TextView) findViewById(R.id.userName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvCourseName = (TextView) findViewById(tv_detail_name);
        tvCourseType = (TextView) findViewById(R.id.tvCourseType);
        tvStartDate = (TextView) findViewById(tv_detail_startdate);
        tvExpPrice = (TextView) findViewById(tv_detail_price);
        tvDescription = (TextView) findViewById(tv_detail_description);

        if (getIntent().getSerializableExtra("itemcauselist") != null) {
            final CauseItemList getcauselist = (CauseItemList) getIntent().getSerializableExtra("itemcauselist");
            tvName.setText("My Name is: "+getcauselist.full_name);
            tvEmail.setText("Email: " + getcauselist.email);
            tvPhone.setText("Phone Number : " + getcauselist.phone);
            tvCourseName.setText(getcauselist.course_title);
            tvCourseType.setText("Course Type: "+getcauselist.CourseName);
            tvStartDate.setText("Start Date on: " + getcauselist.startDate);
            tvExpPrice.setText("I hope the price is about : $ "+getcauselist.exp_price);

            tvDescription.setText(getcauselist.description);

            String path = MySingleton.path + getcauselist.images;
            Picasso.with(getApplicationContext())
                    .load(path)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.noimages)
                    .into(ivUserImage);

        }
    }
}

