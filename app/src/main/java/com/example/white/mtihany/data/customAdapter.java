package com.example.white.mtihany.data;

/**
 * Created by WHITE on 3/18/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.white.mtihany.R;


public class customAdapter extends ArrayAdapter<String> {

    public customAdapter(Context context, String [] Courses) {
        super(context, R.layout.custom_checklist ,Courses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater courseinflator = LayoutInflater.from(getContext());
        View customView = courseinflator.inflate(R.layout.custom_checklist, parent, false);



        String custom_courselist = getItem(position);
        CheckBox checkBox_text = (CheckBox) customView.findViewById(R.id.checkBox1);
        checkBox_text.setText((custom_courselist));


        return customView;

    }
}
