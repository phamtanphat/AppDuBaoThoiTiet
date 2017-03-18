package com.example.administrator.appthoitiet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 1/5/2017.
 */

public class CustomAdapter extends BaseAdapter {
    ArrayList<Thoitiet> arraythoitiet;
    Context mycontext;

    public CustomAdapter(ArrayList<Thoitiet> arraythoitiet, Context mycontext) {
        this.arraythoitiet = arraythoitiet;
        this.mycontext = mycontext;
    }

    @Override
    public int getCount() {
        return arraythoitiet.size();
    }

    @Override
    public Object getItem(int i) {
        return arraythoitiet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dong_list_view,null);
        TextView txtlistday = (TextView) view.findViewById(R.id.textlistday);
        TextView txtstatus = (TextView) view.findViewById(R.id.textviewstatus);
        ImageView imgstatus = (ImageView) view.findViewById(R.id.imageStatus);
        TextView txtmaxtemp = (TextView) view.findViewById(R.id.textviewmaxtemp);
        TextView txtmintemp = (TextView) view.findViewById(R.id.textviewmintemp);

        Thoitiet thoitiet = arraythoitiet.get(i);
        txtstatus.setText(thoitiet.Status);
        txtlistday.setText(thoitiet.Day);
        txtmaxtemp.setText(thoitiet.Maxtemp + "°C");
        txtmintemp.setText(thoitiet.Mintemp + "°C");
        Picasso.with(mycontext).load("http://openweathermap.org/img/w/"+ thoitiet.Image+".png").into(imgstatus);
        return view;
    }
}
