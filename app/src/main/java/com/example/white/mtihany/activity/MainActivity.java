package com.example.white.mtihany.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.white.mtihany.R;
import com.example.white.mtihany.data.CardViewDataAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Courses> coursesList;

    private Button btnSelection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnSelection = (Button) findViewById(R.id.btnShow);

        coursesList = new ArrayList<Courses>();

        for (int i = 1; i <= 15; i++) {
            Courses st = new Courses("Courses " + i, false);

            coursesList.add(st);
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Choose your favorite Courses");

        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an Object for Adapter
        mAdapter = new CardViewDataAdapter(coursesList);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        btnSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = "";
                List<Courses> Cs = ((CardViewDataAdapter) mAdapter)
                        .getCourseList();

                for (int i = 0; i < Cs.size(); i++) {
                    Courses singleCourses = Cs.get(i);
                    if (singleCourses.isSelected() == true) {

                        data = data + "\n" + singleCourses.getName().toString();
                        /*
						 * Toast.makeText( CardViewActivity.this, " " +
						 * singleCourses.getName() + " " +
						 * singleCourses.getEmailId() + " " +
						 * singleCourses.isSelected(),
						 * Toast.LENGTH_SHORT).show();
						 */
                    }

                }

                Toast.makeText(MainActivity.this,
                        "Selected Students: \n" + data, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }


    public void Base (View view){
        startActivity( new Intent(this, BaseActivity.class));
    }

}
