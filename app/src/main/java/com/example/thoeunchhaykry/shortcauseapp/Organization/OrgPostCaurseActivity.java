package com.example.thoeunchhaykry.shortcauseapp.Organization;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.thoeunchhaykry.shortcauseapp.Student.StudentMainActivity;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.thoeunchhaykry.shortcauseapp.MySingleton.url;

public class OrgPostCaurseActivity extends AppCompatActivity {

    ArrayList<CauseItemList> CaurseName;
    ArrayList<String> CaurseNameList = new ArrayList<>();
    CauseItemList itemList;

    Spinner sp_CourseType;
    EditText et_CourseTitle, et_OrgStartDate,et_OrgEndDate,et_ExPrice,et_CaursePostDescription;
    ImageView iv_OrgStartDate,iv_OrgEndDate,iv_OrgPostCourseImage;
    Button btn_PostCourse;
    DatePickerDialog datedialong ;
    Calendar calendar = Calendar.getInstance();

    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    final int CAMERA_CODE=00001;
    final int GALARY_CODE=00002;
    static String SelectPhoto;
    static int rotate=0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
   static String encodingphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_post_caurse);

        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        sp_CourseType = (Spinner)findViewById(R.id.org_CourseType);
        et_CourseTitle = (EditText)findViewById(R.id.et_OrgCaurseTitle);
        et_OrgStartDate =(EditText)findViewById(R.id.et_OrgStartDate);
        et_OrgEndDate = (EditText)findViewById(R.id.et_OrgEndDate);
        et_ExPrice = (EditText)findViewById(R.id.et_ExPrice);
        et_CaursePostDescription = (EditText)findViewById(R.id.et_CaursePostDescription);
        iv_OrgEndDate = (ImageView)findViewById(R.id.iv_OrgEndDate);
        iv_OrgStartDate = (ImageView)findViewById(R.id.iv_OrgSetDate);
        iv_OrgPostCourseImage = (ImageView)findViewById(R.id.iv_OrgPostCourseImage);
        btn_PostCourse = (Button)findViewById(R.id.btn_PostCaurse);

        pref=getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor=pref.edit();
        String fbid = pref.getString("fbid","");
        final String userId = pref.getString("userid","");
        String userType = pref.getString("user_type","");

        String urlCourseType = url+"coursetypelist.php";

        LoadingCourse(urlCourseType,sp_CourseType,"--Select Course Type--");

        iv_OrgStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker(et_OrgStartDate);
            }
        });
        iv_OrgEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker(et_OrgEndDate);
            }
        });
        btn_PostCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrgPostCaurseActivity.this, "PostCourse", Toast.LENGTH_SHORT).show();
            }
        });
        iv_OrgPostCourseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });


        btn_PostCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url3 = MySingleton.url+"postcourse.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url3,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Respone",response);
                                 // LoadingProfile(email);
                                if(!response.equals("false")){
                                    Intent in = new Intent(getApplicationContext(),StudentMainActivity.class);
                                    in.putExtra("requestsesstion","0");
                                    //in.putExtra("user_type",usersList.user_type);
                                    startActivity(in);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                    finish();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error",error.getMessage().toString()+"");
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map <String, String> param = new HashMap<String, String>();


                        Bitmap bitmap = null;
                        try {
                            bitmap = ImageLoader.init().from(SelectPhoto).requestSize(300,300).getBitmap();
                            encodingphoto = ImageBase64.encode(bitmap);
                            param.put("image",encodingphoto);
                            Log.d("PostCourse",encodingphoto+"");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();

                        }finally {


                            param.put("userid",userId);
                            param.put("CourseType",sp_CourseType.getSelectedItemId()+"");
                            param.put("Title",et_CourseTitle.getText().toString().trim());
                            param.put("StartDate",et_OrgStartDate.getText().toString().trim());
                            param.put("EndDate",et_OrgEndDate.getText().toString().trim());
                            param.put("ExPrice",et_ExPrice.getText().toString().trim());
                            param.put("CaursePostDescription",et_CaursePostDescription.getText().toString().trim());
                            return param;
                        }


                    }
                };
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });


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
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrgPostCaurseActivity.this, android.R.layout.simple_list_item_1, CaurseNameList);
                        spinner.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void DateTimePicker(final EditText editText){
        datedialong = new DatePickerDialog(OrgPostCaurseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar getcalendar = Calendar.getInstance();
                getcalendar.set(Calendar.YEAR,year);
                getcalendar.set(Calendar.MONTH,month);
                getcalendar.set(Calendar.DAY_OF_MONTH,day);
                // String thisdate = DateUtils.formatDateTime(getActivity(),getcalendar.getTimeInMillis(),DateUtils.FORMAT_SHOW_DATE );

                String thisdate = year+"-"+(month+1)+"-"+day;

                editText.setText(thisdate);

            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        datedialong.show();
    }

    private void uploadPhoto (){
        AlertDialog.Builder builder = new AlertDialog.Builder(OrgPostCaurseActivity.this);
        builder.setTitle("Choose Options");
        // builder.setIcon(R.mipmap.ic_images);
        builder.setItems(new CharSequence[]
                        {"Take Photo", "Galary"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                if (checkPermissionREAD_EXTERNAL_STORAGE(OrgPostCaurseActivity.this)) {

                                    try {
                                        Intent in = cameraPhoto.takePhotoIntent();
                                        if(Build.VERSION.SDK_INT>=24){
                                            try{
                                                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                                                m.invoke(null);
                                            }catch(Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                        startActivityForResult(in, CAMERA_CODE);
                                        cameraPhoto.addToGallery();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                                break;
                            case 1:
                                if (checkPermissionREAD_EXTERNAL_STORAGE(OrgPostCaurseActivity.this)) {
                                    Intent in = galleryPhoto.openGalleryIntent();
                                    startActivityForResult(in, GALARY_CODE);
                                }
                                break;


                        }
                    }
                });
        builder.create();
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==CAMERA_CODE){
                String PhotoPath = cameraPhoto.getPhotoPath();
                SelectPhoto = PhotoPath;

                try {
                    Bitmap bitmap = ImageLoader.init().from(PhotoPath).requestSize(400,400).getBitmap();

                    iv_OrgPostCourseImage.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if(requestCode==GALARY_CODE){
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String PhotoPath = galleryPhoto.getPath();
                SelectPhoto=PhotoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(PhotoPath).requestSize(400,400).getBitmap();

                    iv_OrgPostCourseImage.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    public Bitmap rotateBitmap(Bitmap soure, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap bitmap = Bitmap.createBitmap(soure,0,0,soure.getWidth(),soure.getHeight(),matrix,true);
        return bitmap;
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;


    //Check Permission READ EXTERNAL STORAGE AND CAMERA >= API23
    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            }else if(ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.CAMERA)) {
                    showDialog("External storage", context,
                            Manifest.permission.CAMERA);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.CAMERA },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            }
            else {
                return true;
            }

        } else {
            return true;
        }
    }

    // show dialog with we request permission .
    public void showDialog(final String msg, final Context context,
                           final String permission) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    // Request Permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(getApplicationContext(), "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

}
