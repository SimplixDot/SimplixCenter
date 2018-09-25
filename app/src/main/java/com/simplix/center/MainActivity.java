package com.simplix.center;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.android.device.DeviceName;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.vansuita.gaussianblur.GaussianBlur;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    String[] maintitle = {
            "System Update", "Wallpapers",
            "Telegram Group", "Frequently Asked Questions",
            "About OS",
    };

    String[] subtitle = {
            "Check for updates", "Pick one of our wallpapers",
            "Leave a feedback or request a feature", "Before you ask anything, check this list first",
            "Simplix Blue Beta 2.0",
    };

    Integer[] imgid = {
            R.drawable.ic_system_update_black_24dp, R.drawable.ic_wallpaper_black_24dp,
            R.drawable.ic_group_black_24dp, R.drawable.ic_question_answer_black_24dp,
            R.drawable.ic_info_black_24dp,
    };

    AlertDialog alertDialog1;
    AlertDialog alertDialog2;
    AlertDialog alertDialog3;
    AlertDialog alertDialog4;
    CharSequence[] values = {" Beta Ring", " Stable Ring"};
    int uchannel = 0;
    final int ostget=1;
    String perm[]={Manifest.permission.READ_EXTERNAL_STORAGE};
    ProgressDialog progress;
    String changelog;
    String latest;
    String dwd;
    String uring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        uchannel = sharedPref.getInt("Channel", 0);
        if (uchannel==0) uring="beta";
        else if (uchannel==1) uring="stable";


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    perm,
                    ostget);
        }
        else {

        }


        setTitle("Simplix Center");

        ListView listView = (ListView)findViewById(R.id.list_view);
        MyListAdapter adapter=new MyListAdapter(this, maintitle, subtitle, imgid);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    progress = ProgressDialog.show(MainActivity.this, "System Update",
                            "Checking for updates...", true);

                    Ion.with(getApplicationContext())
                            .load("https://raw.githubusercontent.com/SimplixDot/platform_vendor_ota/pie/"+DeviceName.getDeviceInfo(MainActivity.this).codename+"/"+"latest-"+uring+".txt")
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result1) {
                                    if (e != null || result1.toLowerCase().contains("fail") || result1.toLowerCase().contains("404: not found")) {
                                        progress.dismiss();
                                        builder.setTitle("Simplix Updates");
                                        builder.setMessage("There was a problem retrieving updates for the selected channel.");
                                        builder.setPositiveButton("OK", new Dialog.OnClickListener() {

                                            public void onClick(DialogInterface arg0, int arg1){
                                                alertDialog2.dismiss();
                                            }

                                        });
                                        alertDialog2 = builder.create();
                                        alertDialog2.show();
                                        return;
                                    }
                                    latest=result1;
                                    Ion.with(getApplicationContext()).load("https://raw.githubusercontent.com/SimplixDot/platform_vendor_ota/pie/"+DeviceName.getDeviceInfo(MainActivity.this).codename+"/"+"changelog-"+uring+".txt").asString()
                                            .setCallback(new FutureCallback<String>() {
                                                @Override
                                                public void onCompleted(Exception e, String result2) {
                                                    if (e != null || result2.toLowerCase().contains("fail") || result2.toLowerCase().contains("404: not found")) {
                                                        progress.dismiss();
                                                        builder.setTitle("Simplix Updates");
                                                        builder.setMessage("There was a problem retrieving updates for the selected channel.");
                                                        builder.setPositiveButton("OK", new Dialog.OnClickListener() {

                                                            public void onClick(DialogInterface arg0, int arg1){
                                                                alertDialog2.dismiss();
                                                            }

                                                        });
                                                        alertDialog2 = builder.create();
                                                        alertDialog2.show();
                                                        return;
                                                    }
                                                    changelog=result2;
                                                    Ion.with(getApplicationContext()).load("https://raw.githubusercontent.com/SimplixDot/platform_vendor_ota/pie/"+DeviceName.getDeviceInfo(MainActivity.this).codename+"/"+"download-"+uring+".txt").asString()
                                                            .setCallback(new FutureCallback<String>() {
                                                                @Override
                                                                public void onCompleted(Exception e, String result3) {
                                                                    if (e != null || result3.toLowerCase().contains("fail") || result3.toLowerCase().contains("404: not found")) {
                                                                        progress.dismiss();
                                                                        builder.setTitle("Simplix Updates");
                                                                        builder.setMessage("There was a problem retrieving updates for the selected channel.");
                                                                        builder.setPositiveButton("OK", new Dialog.OnClickListener() {

                                                                            public void onClick(DialogInterface arg0, int arg1){
                                                                                alertDialog2.dismiss();
                                                                            }

                                                                        });
                                                                        alertDialog2 = builder.create();
                                                                        alertDialog2.show();
                                                                        return;
                                                                    }
                                                                    dwd=result3;
                                                                    if (latest.toLowerCase().contains("beta 2.0.0"))
                                                                    {
                                                                        builder.setTitle("Simplix Updates");
                                                                        builder.setMessage("You already have the latest version installed on your phone.");
                                                                        builder.setPositiveButton("OK", new Dialog.OnClickListener() {

                                                                            public void onClick(DialogInterface arg0, int arg1){
                                                                                alertDialog2.dismiss();
                                                                            }

                                                                        });
                                                                        progress.dismiss();
                                                                        alertDialog2 = builder.create();
                                                                        alertDialog2.show();
                                                                    }
                                                                    else
                                                                    {
                                                                        progress.dismiss();
                                                                        Intent intent = new Intent(MainActivity.this, download.class);
                                                                        intent.putExtra("version", "Beta 2.0");
                                                                        intent.putExtra("device",DeviceName.getDeviceInfo(MainActivity.this).codename);
                                                                        intent.putExtra("uring", uring);
                                                                        intent.putExtra("update", latest);
                                                                        intent.putExtra("dwd", dwd);
                                                                        startActivity(intent);
                                                                    }
                                                                }
                                                            });
                                                }
                                            });
                                }
                            });

                }

                else if(position == 1) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/open?id=1cffU1vJ7EvILQKsqbGkITKooN6JgHrpm"));
                    startActivity(browserIntent);
                }

                else if(position == 2) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Simplix Community");
                    builder.setMessage("We have discussion group and updates channel.");
                    builder.setPositiveButton("Discussion", new Dialog.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://t.me/simplix_group"));
                            startActivity(browserIntent);
                            alertDialog2.dismiss();
                        }

                    });
                    builder.setNeutralButton("Updates", new Dialog.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/simplix_dot"));
                            startActivity(browserIntent);
                            alertDialog2.dismiss();
                        }

                    });
                    alertDialog2 = builder.create();
                    alertDialog2.show();
                    return;




                }

                else if(position == 3) {
                    Intent intent = new Intent(MainActivity.this, faq.class);
                    startActivity(intent);
                }

                else if(position == 4) {
                    Intent intent = new Intent(MainActivity.this, About.class);
                    startActivity(intent);
                }


            }
        });

        FloatingActionButton website = (FloatingActionButton) findViewById(R.id.web_fab);
        website.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://simplixdot.github.io"));
                startActivity(browserIntent);

            }


        });

        FloatingActionButton change = (FloatingActionButton) findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Select your update channel");

                builder.setSingleChoiceItems(values, uchannel, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:
                                SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt("Channel", 0);
                                editor.commit();
                                break;
                            case 1:
                                SharedPreferences sharedPref2 = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = sharedPref2.edit();
                                editor2.putInt("Channel", 1);
                                editor2.commit();
                                break;

                        }
                        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                        uchannel = sharedPref.getInt("Channel", 0);
                        if (uchannel==0) uring="Beta";
                        else if (uchannel==1) uring="Stable";
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = builder.create();
                alertDialog1.show();
            }


        });




    }




}
