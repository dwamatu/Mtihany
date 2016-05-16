package com.example.white.mtihany.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.example.white.mtihany.R;
import com.example.white.mtihany.fragments.WelcomeActivityFragment;
import com.example.white.mtihany.utility.ExternalStorageUtils;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ExternalStorageUtils.createAppDirectories(getApplicationContext());



   /*     if (!PrefSettings.isPressed(getApplicationContext())) {
            Intent intent = new Intent(this, BaseActivity.class);
            startActivity(intent);
            finish();
            return;
        }*/
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);

        if (fragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, new WelcomeActivityFragment()).commit();
        }


         }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }


    //Quick Setup Button Clicked
    public void quickSetup (View  view){

        startActivity(new Intent(this, MainActivity.class));
    }
}
