package com.unipi.ntaf.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<String> user;
    List<String> countryList;
    LayoutInflater inflter;
    List<Timestamp> timestampList;
    String uid;

    public CustomAdapter(Context applicationContext, List<String> countryList, List<String> user, List<Timestamp> timestamps, String uid) {
        this.user = user;
        this.context = context;
        this.timestampList=timestamps;
        this.uid=uid;
        this.countryList = countryList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (user.get(i).equals(uid)) {
            view = inflter.inflate(R.layout.my_message, viewGroup, false);
            TextView country =  view.findViewById(R.id.message_body);
            //ImageView icon =  view.findViewById(R.id.icon);
            country.setText(countryList.get(i));
            //icon.setImageResource(flags[i]);
        }
        else {
            view = inflter.inflate(R.layout.their_message, null);
            TextView country =  view.findViewById(R.id.message_body);
            //ImageView icon =  view.findViewById(R.id.icon);
            country.setText(countryList.get(i));
            //icon.setImageResource(flags[i]);
        }
        return view;
    }
}
