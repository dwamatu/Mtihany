package com.example.white.mtihany.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
;
import android.widget.ListView;

import com.example.white.mtihany.R;
import com.example.white.mtihany.data.customAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        String[] Courses = {"Computer Science","Communication","Peace and Conflict","International Relations","Commerce","Peace and Conflict","Psychology",
                "Community Development","Management and Info Systems","Theology","Actuarial Science","Enviromental Science","Political Science","Economics","Marketing"};
        ArrayAdapter CoursesAdapter = new customAdapter(getActivity(),Courses);
        ListView CheckBoxListView = (ListView) view.findViewById(R.id.CheckBox_List);

        CheckBoxListView.setAdapter(CoursesAdapter);

        CheckBoxListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.v("TAG", "CLICKED row number: " + arg2);
            }

        });
        return view;
    }


}
