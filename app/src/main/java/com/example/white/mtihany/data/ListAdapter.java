package com.example.white.mtihany.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.white.mtihany.R;

/**
 * Created by WHITE on 3/19/2016.
 */
public class ListAdapter extends ArrayAdapter<String> {

    public ListAdapter(Context context, String [] Courses) {
        super(context, R.layout.custom_checklist ,Courses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater courseinflator = LayoutInflater.from(getContext());
        View customView = courseinflator.inflate(R.layout.document_item_list, parent, false);



        String custom_documentlist = getItem(position);
        TextView documentsText = (TextView) customView.findViewById(R.id.documentstext);
        documentsText.setText((custom_documentlist));


        return customView;

    }
}


