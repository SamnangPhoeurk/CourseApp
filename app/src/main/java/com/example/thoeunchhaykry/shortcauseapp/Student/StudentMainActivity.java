package com.example.thoeunchhaykry.shortcauseapp.Student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thoeunchhaykry.shortcauseapp.CauselistActivity;
import com.example.thoeunchhaykry.shortcauseapp.HomeActivity;
import com.example.thoeunchhaykry.shortcauseapp.Organization.OrgPostCaurseActivity;
import com.example.thoeunchhaykry.shortcauseapp.Organization.RequestCourseActivity;
import com.example.thoeunchhaykry.shortcauseapp.R;
import com.example.thoeunchhaykry.shortcauseapp.RoundedCornersTransform;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

public class StudentMainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    String getrequest="";
    ImageView Iv_profile;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_student_main);

        toolbar=(Toolbar)findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);
        mDrawerlayout=(DrawerLayout)findViewById(R.id.navigationDrawer);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,mDrawerlayout,R.string.Open,R.string.Close);
        mDrawerlayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref=getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor=pref.edit();




        Intent getin = getIntent();
        getrequest=getin.getStringExtra("requestsesstion");
        if (getrequest.equals("1")){
            RequestFragment requestFragment = new RequestFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.main_linear,requestFragment)
                    .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                    .commit();
        }else {
            StudentHomeFragment studentHomeFragment = new StudentHomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main_linear,studentHomeFragment).commit();

        }


        navigationView=(NavigationView)findViewById(R.id.navigation);
//        navigationView.inflateHeaderView(R.layout.fragment_header);

//==== Get picture to make user profile =====
        String fbid = pref.getString("fbid","");
        String userId = pref.getString("userid","");
        String userType = pref.getString("user_type","");

        View headerView = getLayoutInflater().inflate(R.layout.fragment_header, mDrawerlayout, false);
        navigationView.addHeaderView(headerView);
        //=== Check UserType as Organization
        if(userType.equals("2")){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.new_navigation_menu);
        }

        Iv_profile=(ImageView)headerView.findViewById(R.id.Iv_profilestudent);
        if (fbid.equals("")){
            Picasso.with(getApplicationContext())
                    .load(R.drawable.defaultprofilem)
                    .placeholder(R.drawable.profileicon)
                    .error(R.drawable.com_facebook_profile_picture_blank_portrait)
                    .transform(new RoundedCornersTransform())
                    .into(Iv_profile);
        }else {
            Picasso.with(getApplicationContext())
                    .load("https://graph.facebook.com/" + fbid+ "/picture?type=large")
                    .placeholder(R.drawable.profileicon)
                    .error(R.drawable.com_facebook_profile_picture_blank_portrait)
                    .transform(new RoundedCornersTransform())
                    .into(Iv_profile);
        }

       // Iv_profile.setImageResource("https://graph.facebook.com/" +fbid+ "/picture?type=large");

       Iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "setting menu", Toast.LENGTH_SHORT).show();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id==R.id.home_menu){
                    mDrawerlayout.closeDrawers();
                    getSupportActionBar().setTitle("Main Cause");
                    StudentHomeFragment studentHomeFragment = new StudentHomeFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.main_linear,studentHomeFragment)
                            .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                            .commit();
                    return true;
                }else if (id==R.id.request_menu){
                    item.setChecked(true);
                    mDrawerlayout.closeDrawers();
                    getSupportActionBar().setTitle("Request Cause");
                    RequestFragment requestFragment = new RequestFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.main_linear,requestFragment)
                            .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                            .commit();

                    return true;
                }else if (id==R.id.alert_menu){
                    item.setChecked(true);
                    mDrawerlayout.closeDrawers();
                    getSupportActionBar().setTitle("Alert");
                    Toast.makeText(getApplicationContext(),"Alert menu", Toast.LENGTH_SHORT).show();
                    return true;
                }else if (id==R.id.favorite_menu){
                    item.setChecked(true);
                    mDrawerlayout.closeDrawers();
                    //getSupportActionBar().setTitle("Favorite");
                    Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                    in.putExtra("coursetypeid","favorite");
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    return true;
                }else if (id==R.id.map_menu){
                    item.setChecked(true);
                    mDrawerlayout.closeDrawers();
                    getSupportActionBar().setTitle("Map");
                    Toast.makeText(getApplicationContext(), "map menu", Toast.LENGTH_SHORT).show();
                    MapFragment mapfragment = new MapFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.main_linear,mapfragment).commit();
                    return true;
                }else if (id==R.id.profile_menu){
                    item.setChecked(true);
                    mDrawerlayout.closeDrawers();
                    getSupportActionBar().setTitle("Profile");
                    UserprofileFragment userprofileFragment = new UserprofileFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.main_linear,userprofileFragment)
                            .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                            .commit();
                    return true;
                }else if (id==R.id.setting_menu){
                    item.setChecked(true);
                    mDrawerlayout.closeDrawers();
                    getSupportActionBar().setTitle("Setting");
                    SettingFragment settingFragment = new SettingFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.main_linear,settingFragment)
                            .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                            .commit();
                    return true;
                }else if (id==R.id.logout_menu){
                    item.setChecked(true);
                    mDrawerlayout.closeDrawers();
                    showDialog();
                    return true;
                }else if(id==R.id.post_menu){
                    item.setChecked(true);
                    mDrawerlayout.closeDrawers();
                    getSupportActionBar().setTitle("Post Subject");
                    Toast.makeText(getApplicationContext(), "Post Subject", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(),OrgPostCaurseActivity.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    return true;
                }else if (id==R.id.myCourse_menu){
                    item.setChecked(true);
                    mDrawerlayout.closeDrawers();
                    getSupportActionBar().setTitle("My Course");
                    Toast.makeText(getApplicationContext(), "MY COURSE", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(),CauselistActivity.class);
                    in.putExtra("coursetypeid","My Course");
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    return true;
                }else if (id==R.id.couresRequested_menu){
                    item.setChecked(true);
                    mDrawerlayout.closeDrawers();
                    getSupportActionBar().setTitle("Course Requested");
                    Toast.makeText(getApplicationContext(), " COURSE Resquested", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(),RequestCourseActivity.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item_menu = menu.findItem(R.id.home_menu);
        MenuItem item_menu1 = menu.findItem(R.id.logout_menu);
        MenuItem item_menu2 = menu.findItem(R.id.request_menu);
        MenuItem item_menu3 = menu.findItem(R.id.alert_menu);
        MenuItem item_menu4 = menu.findItem(R.id.map_menu);
        MenuItem item_menu5 = menu.findItem(R.id.favorite_menu);
        MenuItem item_menu6 = menu.findItem(R.id.profile_menu);
        MenuItem item_menu7 = menu.findItem(R.id.setting_menu);
        item_menu.setVisible(false);
        item_menu1.setVisible(false);
        item_menu2.setVisible(false);
        item_menu3.setVisible(false);
        item_menu4.setVisible(false);
        item_menu5.setVisible(false);
        item_menu6.setVisible(false);
        item_menu7.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() throws Resources.NotFoundException {
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Are you sure to Log out ?")
                .setIcon(R.drawable.logout)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                mDrawerlayout.closeDrawers();
                                editor=pref.edit();
                                editor.clear();
                                editor.commit();
                                Intent in = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                                LoginManager.getInstance().logOut();

                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                mDrawerlayout.closeDrawers();
                                Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
    }
}

