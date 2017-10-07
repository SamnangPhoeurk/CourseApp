package com.example.thoeunchhaykry.shortcauseapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.FacebookSdk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by thoeunchhaykry on 12/28/2016.
 */

public class CauseItemAdapter extends RecyclerView.Adapter<CauseItemAdapter.FindViewHolder>{

    private ArrayList<CauseItemList> mCauseitemlist;
    private Context mContext;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int status;
    int statustag=0;

    public CauseItemAdapter(Context context, ArrayList<CauseItemList> causeItemLists){
        this.mCauseitemlist=causeItemLists;
        this.mContext = context;

    }

    @Override
    public FindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cause_itemlist_layout,parent,false);
        FindViewHolder holder=new FindViewHolder(view);
        FacebookSdk.sdkInitialize(mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(final FindViewHolder holder, int position) {
        YoYo.with(Techniques.Landing).playOn(holder.cv_causeitem);
        final CauseItemList causeItemList = mCauseitemlist.get(position);
        holder.tv_title.setText(causeItemList.pc_title);
        holder.tv_startdate.setText(causeItemList.startdate);
        holder.tv_enddate.setText(causeItemList.enddate);
        holder.tv_price.setText(causeItemList.price+" $");
        holder.tv_pcid.setText(causeItemList.pc_id+"");
//        statustag = causeItemList.status;
        holder.Ivl_tag.setImageResource(R.mipmap.tagblack_icon);


        preferences=mContext.getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor=preferences.edit();

        final String userid = preferences.getString("userid","");
        final String getemail=preferences.getString("email","");
        final String fbid = preferences.getString("fbid","");
        Log.d(TAG, "userid : "+userid);
       // final String getpassword=preferences.getString("password","");

//        if (!(getemail.equals(""))){
//            String url =MySingleton.url+"";
//            if (statustag==0){
//                holder.Ivl_tag.setImageResource(R.mipmap.tagblack_icon);
//            }else {
//                holder.Ivl_tag.setImageResource(R.mipmap.tagpink_icon);
//            }
//        }else {
//            holder.Ivl_tag.setImageResource(R.mipmap.tagblack_icon);
//        }


        String path=MySingleton.path+causeItemList.pc_image;
//        String path="http://10.0.3.2/shortcauseapp/Images/"+causeItemList.pc_image;
        Picasso.with(mContext)
                .load(path)
                .placeholder(R.drawable.loading)
                .error(R.drawable.noimages)
                .into(holder.iv_images);
        holder.cv_causeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(mContext,DetailCauseActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CauseItemList causelists = (CauseItemList) causeItemList;
                in.putExtra("itemcauselist",causelists);
                mContext.startActivity(in);
                Activity activity =(Activity)holder.itemView.getContext();
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
// save a favorite to database

        holder.Ivl_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,causeItemList.pc_id+"", Toast.LENGTH_SHORT).show()
//                Log.d("userid","userid: "+userid);
                if (!(getemail.equals(""))||!(fbid.equals(""))){
                    if (status==0){
                        holder.Ivl_tag.setImageResource(R.mipmap.tagpink_icon);
                        status=1;
                    }else {
                        holder.Ivl_tag.setImageResource(R.mipmap.tagblack_icon);
                        status=0;
                    }

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
                            Log.d("error",error.getMessage());
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("userid",userid);
                            params.put("pc_id",causeItemList.pc_id+"");
                            params.put("status",status+"");
                            return params;
                        }
                    };
                    MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
                }else {
                    Intent in = new Intent(getApplicationContext(),LoginActivity.class);
//                    in.putExtra("requestsesstion","0");
                    Activity activity =(Activity)holder.itemView.getContext();
                    activity.startActivity(in);
                    activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }

            }
        });

        holder.Ivl_share.setOnClickListener(new View.OnClickListener() {
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
                mContext.startActivity(chooserIntent);


            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCauseitemlist ==null){
            return 0;
        }else {
            return mCauseitemlist.size();
        }

    }


    public static class FindViewHolder extends RecyclerView.ViewHolder {

        public CardView cv_causeitem;
        public TextView tv_title;
        public TextView tv_startdate;
        public TextView tv_enddate;
        public TextView tv_price;
        public TextView tv_pcid;
        public ImageView iv_images,Ivl_tag,Ivl_share;

        public FindViewHolder(View itemView){
            super(itemView);

            cv_causeitem=(CardView)itemView.findViewById(R.id.cv_causeitem);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            tv_startdate=(TextView)itemView.findViewById(R.id.tv_startdate);
            tv_enddate=(TextView)itemView.findViewById(R.id.tv_enddate);
            iv_images=(ImageView)itemView.findViewById(R.id.iv_images);
            Ivl_tag=(ImageView)itemView.findViewById(R.id.Ivl_tag);
            Ivl_share=(ImageView)itemView.findViewById(R.id.Ivl_share);
            tv_price=(TextView)itemView.findViewById(R.id.tv_price);
            tv_pcid=(TextView)itemView.findViewById(R.id.tv_pcid);

        }

    }

    public void setFilter(ArrayList<CauseItemList> newcuaselist){
        mCauseitemlist = new ArrayList<>();
        if (mCauseitemlist.contains("")){

        }else {
            mCauseitemlist.addAll(newcuaselist);
            notifyDataSetChanged();
        }

    }
}
