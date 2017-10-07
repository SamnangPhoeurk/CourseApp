package com.example.thoeunchhaykry.shortcauseapp.Organization;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.thoeunchhaykry.shortcauseapp.CauseItemList;
import com.example.thoeunchhaykry.shortcauseapp.R;
import com.facebook.FacebookSdk;

import java.util.ArrayList;

/**
 * Created by PSN on 9/26/17.
 */

public class RequestCourseAdapter extends RecyclerView.Adapter<RequestCourseAdapter.FindViewHolder> {
    private ArrayList<CauseItemList> mRequestCourse;
    private Context mContext;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int status;
    int statustag=0;

    public RequestCourseAdapter(Context context, ArrayList<CauseItemList> requestCourse){
        this.mRequestCourse=requestCourse;
        this.mContext = context;

    }

    @Override
    public FindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_request_layout,parent,false);
        FindViewHolder holder=new FindViewHolder(view);
        FacebookSdk.sdkInitialize(mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(FindViewHolder holder, int position) {
        YoYo.with(Techniques.Landing).playOn(holder.cvRequestCourse);
        final CauseItemList ReqCourse = mRequestCourse.get(position);
        holder.tvReqTitle.setText("I want to Learn: "+ReqCourse.course_title);
        holder.tvStartDate.setText("Course Will Start On: "+ReqCourse.startDate);
        holder.tvSession.setText("The Session is: "+ReqCourse.Session);
        holder.tvPrice.setText("The Price is about: $ "+ReqCourse.exp_price );

        holder.cvRequestCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "Test Click", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(mContext,RequestCourseDetialActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CauseItemList causelists = (CauseItemList) ReqCourse;
                in.putExtra("itemcauselist",causelists);
                mContext.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mRequestCourse ==null){
            return 0;
        }else {
            return mRequestCourse.size();
        }
    }


    public static class FindViewHolder extends RecyclerView.ViewHolder {
        public CardView cvRequestCourse;
        public TextView tvReqTitle, tvSession, tvStartDate,tvPrice;


        public FindViewHolder(View itemView){
            super(itemView);

            cvRequestCourse = (CardView)itemView.findViewById(R.id.cv_ReqCourse);
            tvReqTitle = (TextView)itemView.findViewById(R.id.tv_ReqTitle);
            tvSession=(TextView)itemView.findViewById(R.id.tv_Session);
            tvStartDate=(TextView)itemView.findViewById(R.id.tv_StartDate);
            tvPrice=(TextView)itemView.findViewById(R.id.tv_price);

        }

    }
    public void setFilter(ArrayList<CauseItemList> newcuaselist){
        mRequestCourse = new ArrayList<>();
        if (mRequestCourse.contains("")){

        }else {
            mRequestCourse.addAll(newcuaselist);
            notifyDataSetChanged();
        }

    }
}
