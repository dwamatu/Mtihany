package com.example.white.mtihany.activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.white.mtihany.R;
import com.example.white.mtihany.fragments.DocumentFragment;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;

import butterknife.ButterKnife;


public class BaseActivity extends AppCompatActivity {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    final FTPClient ftp = new FTPClient();
    public ArrayList<String> courseD = new ArrayList<>();
    public String data = new String();
    int fl = 0;
    boolean status = false;
    FTPFile[] filesList = null;
    ProgressDialog mProgressDialog;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_base, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Fragment Document = new DocumentFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.base_content, Document).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


    }
    public boolean delete(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You cannot delete " +
                "Kamau Wamatu")
                .setNegativeButton("okay", null)
                .create()
                .show();
        return true;

    }

    public void Download(MenuItem item) {
        final class Des extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(BaseActivity.this);
                mProgressDialog.setTitle("Downloading ");
                mProgressDialog.setMessage("Creating Required Directories..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();


            }

            @Override
            protected String doInBackground(String... params) {
                String folder = String.valueOf(courseD.toString());
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

                    ftp.changeWorkingDirectory((String.valueOf(data)+"/"));
                    filesList = ftp.listFiles("/mtihany/"+String.valueOf(data)+ "/");
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
                    Intent intent = new Intent(BaseActivity.this, BaseActivity.class);
                    PendingIntent pIntent = PendingIntent.getActivity(BaseActivity.this, 0, intent, 0);

                    Notification noti = new Notification.Builder(BaseActivity.this)
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

            }


        }
        new Des().execute();
        Toast.makeText(getApplicationContext(), "Download is Pressed", Toast.LENGTH_LONG).show();

    }
}