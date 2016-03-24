package com.example.white.mtihany.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.white.mtihany.R;
import com.example.white.mtihany.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeActivityFragment extends Fragment {


    public WelcomeActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }


}
