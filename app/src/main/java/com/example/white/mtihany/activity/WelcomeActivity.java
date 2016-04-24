package com.example.white.mtihany.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.white.mtihany.R;
import com.example.white.mtihany.fragments.WelcomeActivityFragment;
import com.example.white.mtihany.utility.ExternalStorageUtils;
import com.example.white.mtihany.utility.PrefSettings;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        ExternalStorageUtils.createAppDirectories(getApplicationContext());



       /* if (!PrefSettings.isPressed(getApplicationContext())) {
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

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //Quick Setup Button Clicked
    public void quickSetup (View  view){
        PrefSettings.markPressedin(this, false);
        startActivity(new Intent(this, MainActivity.class));
    }
}
