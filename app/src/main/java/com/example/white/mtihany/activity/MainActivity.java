package com.example.white.mtihany.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.white.mtihany.R;
import com.example.white.mtihany.data.CardViewDataAdapter;
import com.example.white.mtihany.data.Courses;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Recycler View

    //FTP
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    final FTPClient ftp = new FTPClient();
    public ArrayList<String> courseD = new ArrayList<>();
    public String data = new String();
    int fl = 0;
    public boolean downloadStatus=false;
    boolean status = false;
    FTPFile[] filesList = null;
    ProgressDialog mProgressDialog;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Courses> coursesList;
    private Button btnSelection;
    private ProgressDialog dialog;

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
                courseD.clear();
                for (int i = 0; i < Cs.size(); i++) {
                    Courses singleCourses = Cs.get(i);
                    if (singleCourses.isSelected() == true) {

                        data = data + "\n" + singleCourses.getName().toString();

                        if (courseD.size() > 0) {

                            courseD.add(singleCourses.getName().toString());
                        } else {
                            courseD.add(singleCourses.getName().toString());

                        }

                        /*
						 * Toast.makeText( CardViewActivity.this, " " +
						 * singleCourses.getName() + " " +
						 * Toast.LENGTH_SHORT).show();
						 */
                    }

                }

                    for (int j=0;j<courseD.size();j++){
                        new Des().execute(courseD.get(j).toString());

                    }

                

                Toast.makeText(MainActivity.this,
                        "Selected Students: \n" + String.valueOf(data), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }


    public void Base (View view){

               startActivity( new Intent(this, BaseActivity.class));
    }

    final class Des extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Downloading ");
            mProgressDialog.setMessage("Creating Required Directories..");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            String folder = String.valueOf(courseD.toString());
            String course=params[0];
            File root = android.os.Environment.getExternalStorageDirectory();

            File dir = new File(root.getAbsolutePath() + "/mtihany/documents"); //it is my root directory

            File cur = new File(dir.getAbsolutePath() + "/" + folder + "/"); // it is my sub folder directory .. it can vary..

            try {
                if (dir.exists() == false) {

                    dir.mkdirs();
                    mProgressDialog.setTitle("Downloading " + String.valueOf(courseD));
                    mProgressDialog.setMessage("Creating Required Directories..");
                } else {
                    mProgressDialog.setTitle("Updating " + String.valueOf(data));
                    mProgressDialog.setMessage("Please Wait..");
                }
                   /* if(cur.exists()==false)
                    {
                        cur.mkdirs();
                        mProgressDialog.setTitle("Downloading "+courseD.toString());
                        mProgressDialog.setMessage("Creating Required Directories..");


                    }
                    else{
                        mProgressDialog.setTitle("Updating "+courseD.toString());
                        mProgressDialog.setMessage("Please Wait..");



                    }*/


                ftp.connect(InetAddress.getByName("kimeumana.freevar.com"));
//FTP username and password authentication
                ftp.login("kimeumana.freevar.com", "Melt348");
                ftp.enterLocalPassiveMode();
//To change directory of FTP Server

                ftp.changeWorkingDirectory("/mtihany/"+course+"/");
                filesList = ftp.listFiles("");
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.setBufferSize(102400);
                while (fl < filesList.length) {
                    try {
                        Log.v("File name", filesList[fl].getName());

                        try {

                            FileOutputStream desFileStream = new FileOutputStream(dir + File.separator + filesList[fl].getName().toString());


                            status = ftp.retrieveFile(filesList[fl].getName().toString(), desFileStream);

                            mProgressDialog.setMessage("Downloading " + filesList[fl].getName().toString());
                            mProgressDialog.setTitle("Downloading " + String.valueOf(courseD));

                            desFileStream.flush();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    fl++;

                }

                ftp.logout();
                ftp.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @SuppressLint("NewApi")
        @Override

        protected void onPostExecute(String result) {
            if (status == true) {
                mProgressDialog.setMessage("Downloaded All Files");
                mProgressDialog.setTitle("Success");

                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                Notification noti = new Notification.Builder(MainActivity.this)
                        .setContentTitle("Downloaded ")
                        .setContentText("")
                        .setSound(uri)
                        .setAutoCancel(true)
                        .setContentIntent(pIntent)
                        .build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                // hide the notification after its selected
                notificationManager.notify(0, noti);


                // Intent i = new Intent(MainActivity.this, MainCourseList.class);
                // startActivity(i);

            } else {
                Toast.makeText(getApplicationContext(), "Download is Incomplete\nCheck your Network Connetion", 1).show();
            }
            mProgressDialog.dismiss();
            downloadStatus = true;

        }


    }

}
