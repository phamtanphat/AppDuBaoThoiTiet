package com.example.administrator.appthoitiet;

/**
 * Created by Administrator on 1/5/2017.
 */
public class Thoitiet {
    public String Day;
    public String Status;
    public String Image;
    public String Maxtemp;
    public String Mintemp;

    public Thoitiet(String day,String status, String image, String maxtemp, String mintemp) {
        Day = day;
        Status = status;
        Image = image;
        Maxtemp = maxtemp;
        Mintemp = mintemp;
    }
}
