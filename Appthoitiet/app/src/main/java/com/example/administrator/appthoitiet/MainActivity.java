package com.example.administrator.appthoitiet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText edtsearch;
    Button btnsearch, btnintent;
    ImageView img;
    TextView txtname, txtcountry, txtngaycapnhat, txttrangthai, txtdoam, txtmay, txthientai, txtgio;
    String url;
    String quocgia = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        GetDataWeather("Saigon");
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quocgia = edtsearch.getText().toString().trim();
                GetDataWeather(quocgia);

            }
        });
        btnintent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quocgia = edtsearch.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("tencountry",quocgia);
                startActivity(intent);
            }
        });
    }

    public void GetDataWeather(String tencountry) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        url = "http://api.openweathermap.org/data/2.5/weather?q="+tencountry+"&mode=json&units=metric&appid=53fbf527d52d4d773e828243b90c1f8e";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String name = jsonObject.getString("name");
                            String weather = jsonObject.getString("weather");
                            String main = jsonObject.getString("main");
                            String wind = jsonObject.getString("wind");
                            String cloud = jsonObject.getString("clouds");
                            String day = jsonObject.getString("dt");

                            long unixSeconds = Long.valueOf(day);
                            Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
                            SimpleDateFormat sdf = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss "); // the format of your date
                            String formattedDate = sdf.format(date);
                            txtngaycapnhat.setText(formattedDate);

                            String sys = jsonObject.getString("sys");

                            txtname.setText("Tên thành phố : "+name);
                            JSONArray jsonArrayWeather = new JSONArray(weather);
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String image = jsonObjectWeather.getString("icon");
                            txttrangthai.setText(status);
                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+image+".png").into(img);

                            JSONObject jsonObjectMain = new JSONObject(main);
                            String temp = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");

                            Double NhietDo = Double.valueOf(temp);
                            String Temp = String.valueOf(NhietDo.intValue());

                            txthientai.setText(Temp+"°C");
                            txtdoam.setText(doam + "%");

                            JSONObject jsonObjectWind = new JSONObject(wind);
                            String speed = jsonObjectWind.getString("speed");
                            txtgio.setText(speed+"m/s");

                            JSONObject jsonObjectCloud = new JSONObject(cloud);
                            String clouds = jsonObjectCloud.getString("all");
                            txtmay.setText(clouds + "%");

                            JSONObject jsonObjectSys = new JSONObject(sys);
                            String country = jsonObjectSys.getString("country");
                            txtcountry.setText("Tên đất nước : "+country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
//        edtsearch.setText("");
    }

    private void Anhxa() {
        edtsearch = (EditText) findViewById(R.id.edittextsearch);
        btnsearch = (Button) findViewById(R.id.buttonsearch);
        img = (ImageView) findViewById(R.id.imageView);
        txtname = (TextView) findViewById(R.id.textten);
        txtcountry = (TextView) findViewById(R.id.textcountry);
        txtngaycapnhat = (TextView) findViewById(R.id.textviewdayupdate);
        txttrangthai = (TextView) findViewById(R.id.texttrangthai);
        txtdoam = (TextView) findViewById(R.id.textdoam);
        txtmay = (TextView) findViewById(R.id.textmay);
        txthientai = (TextView) findViewById(R.id.textnhietdohientai);
        txtgio = (TextView) findViewById(R.id.textwind);
        btnintent = (Button) findViewById(R.id.buttonintent);
    }
}
