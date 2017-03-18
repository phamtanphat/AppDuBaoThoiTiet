package com.example.administrator.appthoitiet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    ListView lv;
    String tenquocgia = "";
    ImageView imgback;
    TextView txtname;
    ArrayList<Thoitiet> arrayThoitiet;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lv = (ListView) findViewById(R.id.listviewdsngay);
        txtname = (TextView) findViewById(R.id.textviewname);
        imgback = (ImageView) findViewById(R.id.imageback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arrayThoitiet = new ArrayList<Thoitiet>();
        Intent intent = getIntent();
        String a = intent.getStringExtra("tencountry");
        if (a.equals("")){
            tenquocgia = "Saigon";
            GetDatafor7days(tenquocgia);
            Log.d("aaaaaaaa","Chưa có gì " + tenquocgia);
        }else {
            tenquocgia = a;
            Log.d("aaaaaaaa","Có dữ liệu" + tenquocgia);
            GetDatafor7days(tenquocgia);
        }

    }
    public void GetDatafor7days(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url ="http://api.openweathermap.org/data/2.5/forecast/daily?q="+data+"&mode=json&units=metric&cnt=7&appid=53fbf527d52d4d773e828243b90c1f8e";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String list = jsonObject.getString("list");
                    JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                    String Name = jsonObjectCity.getString("name");
                    txtname.setText(Name);
                    Log.d("mangketqua",Name);

                    JSONArray jsonArrayList = new JSONArray(list);
                    for (int i = 0 ; i<jsonArrayList.length();i++){
                        JSONObject jsonObjectListday = jsonArrayList.getJSONObject(i);
                        String Ngay = jsonObjectListday.getString("dt");
                        JSONObject jsonObjectTemp = jsonObjectListday.getJSONObject("temp");
                        String mintemp = jsonObjectTemp.getString("min");
                        String maxtemp = jsonObjectTemp.getString("max");

                        Double ValueMintemp = Double.valueOf(mintemp);
                        Double ValueMaxtemp = Double.valueOf(maxtemp);
                        String Min = String.valueOf(ValueMintemp.intValue());
                        String Max = String.valueOf(ValueMaxtemp.intValue());

                        long unixSeconds = Long.valueOf(Ngay);
                        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE yyyy-MM-dd "); // the format of your date
                        String formattedDate = sdf.format(date);

//                        Log.d("ngaycacbuoi",formattedDate);

                        JSONArray jsonArrayWeather = jsonObjectListday.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String image = jsonObjectWeather.getString("icon");
                        String status = jsonObjectWeather.getString("description");
                        arrayThoitiet.add(new Thoitiet(formattedDate,status,image,Max,Min));
                    }
                    customAdapter = new CustomAdapter(arrayThoitiet,getApplication());
                    lv.setAdapter(customAdapter);
                    customAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
