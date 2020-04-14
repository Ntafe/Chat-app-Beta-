package com.unipi.ntaf.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.firestore.auth.User;

import java.sql.Timestamp;
import java.util.List;

public class UserAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    List<String> mUsers;

    public UserAdapter(Context applicationContext, List<String> mUsers){
        this.mUsers=mUsers;
        this.context = context;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.my_message, viewGroup, false);
        TextView country =  view.findViewById(R.id.message_body);
        //ImageView icon =  view.findViewById(R.id.icon);
        country.setText(mUsers.get(i));
        //icon.setImageResource(flags[i]);
        return view;
    }
}
