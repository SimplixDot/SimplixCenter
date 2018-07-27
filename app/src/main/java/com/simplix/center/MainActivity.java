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
            "Simplix Orange Beta 1.7.3",
    };

    Integer[] imgid = {
            R.drawable.ic_system_update_black_24dp, R.drawable.ic_wallpaper_black_24dp,
            R.drawable.ic_message_black_24dp, R.drawable.ic_assignment_black_24dp,
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

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(layoutParams);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    perm,
                    ostget);
        }
        else {
            final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
            ImageView bgiv = (ImageView)findViewById(R.id.bgiv);
            GaussianBlur.with(this).put(wallpaperDrawable, bgiv);
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
                    .load("https://raw.githubusercontent.com/SimplixDot/platform_vendor_ota/oreo/latest-"+uring+"/"+DeviceName.getDeviceInfo(MainActivity.this).codename+".txt")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result1) {
                            if (e != null || result1.toLowerCase().contains("fail")) {
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
                            Ion.with(getApplicationContext()).load("https://raw.githubusercontent.com/SimplixDot/platform_vendor_ota/oreo/changelogs-"+uring+"/"+DeviceName.getDeviceInfo(MainActivity.this).codename+".txt").asString()
                                    .setCallback(new FutureCallback<String>() {
                                        @Override
                                        public void onCompleted(Exception e, String result2) {
                                            if (e != null || result2.toLowerCase().contains("fail")) {
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
                                            Ion.with(getApplicationContext()).load("https://raw.githubusercontent.com/SimplixDot/platform_vendor_ota/oreo/downloads-"+uring+"/"+DeviceName.getDeviceInfo(MainActivity.this).codename+".txt").asString()
                                            .setCallback(new FutureCallback<String>() {
                                                @Override
                                                public void onCompleted(Exception e, String result3) {
                                                    if (e != null || result3.toLowerCase().contains("fail")) {
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
                                                    if (latest.toLowerCase().contains("beta 1.7.3"))
                                                    {
                                                        builder.setTitle("Simplix Updates");
                                                        builder.setMessage("You already have the latest version installed on your phone.");
                                                        builder.setPositiveButton("OK", new Dialog.OnClickListener() {

                                                            public void onClick(DialogInterface arg0, int arg1){
                                                                alertDialog2.dismiss();
                                                            }

                                                        });
                                                    }
                                                    else
                                                    {
                                                        builder.setTitle("Simplix " + latest);
                                                        builder.setMessage("There is a new update available for your device!" + System.getProperty("line.separator") + changelog);
                                                        builder.setPositiveButton("Download now", new Dialog.OnClickListener(){

                                                            public void onClick(DialogInterface arg0, int arg1){
                                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dwd));
                                                                startActivity(browserIntent);
                                                            }

                                                        });
                                                        builder.setNegativeButton("No, thanks", new Dialog.OnClickListener() {

                                                            public void onClick(DialogInterface arg0, int arg1){
                                                                alertDialog2.dismiss();
                                                            }

                                                        });
                                                    }
                                                    progress.dismiss();
                                                    alertDialog2 = builder.create();
                                                    alertDialog2.show();
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
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://t.me/simplix_group"));
                    startActivity(browserIntent);
                }

                else if(position == 3) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Frequently Asked Questions");
                    builder.setMessage("Q: How to report a bug?\n" +
                            "A: Send a logcat in your device's XDA thread or in our Telegram group. To retrieve a logcat before the device has booted use \"adb logcat -d\" and to retrieve one during the boot process and after it, use just \"adb logcat\".\n" +
                            "\n" +
                            "Q: Why does this ROM try to convince people it's innovative when you don't have feature X?\n" +
                            "A: Simplix is basically brand new. We try to add as many *useful* and convenient features as we can. If you think feature X would really improve your experience, then feel free to request it in the Telegram group. Beware though that bloating the ROM with apps and other things you can add yourself pretty easily *IS NOT* making it better. It's just bloating it. Maybe Simplix just isn't your ROM. You need Complix (still under construction)\n" +
                            "\n" +
                            "Q: How is this ROM even different than CosmicOS? You just got the source and switched the logo...\n" +
                            "A: We already have several major improvements from CosmicOS. One of them is the fact that we try to improve the experience on all devices. Unlike other ROMs, here we are ready to switch device trees anytime and edit them, so that you have the best experience you could possibly get out of your phone. Are you experienced enough to be a maintainer? Just ask in the Telegram group. You don't want to be a maintainer but you have an advise on how we can make a certain device work better on our ROM? Tell us in the Telegram group.\n" +
                            "\n" +
                            "Q: How quickly will I get updates?\n" +
                            "A: Simplix will get updates 1-3 times a month. You can call it the \"AndroidTwo program\" if you want. ;P Recieving the updates on your phone, however, depends on the maintainer. You can get stable releases 3 times a month (in the best case), or receive betas 2 times a year (in the worst case). If you don't want to wait, you can always build the ROM yourself by cloning our repo over at http://github.com/SimplixDot\n" +
                            "\n" +
                            "Q: What is the difference between the Beta ring and the Stable ring?\n" +
                            "A: If glitches are expected from time to time, the build belongs to the Beta ring. Beta builds are still usable for daily driver. If no issues get in your way overall throughout your experience, the build belongs to the Stable ring. Can't wait for new features and don't mind a few bugs? Join the Beta ring.");
                    builder.setPositiveButton("Close", new Dialog.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1){
                            alertDialog3.dismiss();
                        }

                    });
                    alertDialog3 = builder.create();
                    alertDialog3.show();
                }

                else if(position == 4) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Changelog");
                    builder.setMessage("What's new in Beta 1.7.3 for "+DeviceName.getDeviceInfo(MainActivity.this).codename+"\n" +
                            "- Redesigned About Phone page\n" +
                            "- When QS Tile Titles are hidden, long pressing a tile will now open detail view\n" +
                            "- \"Simplix.\" rebrand to just \"Simplix\" is now more noticeable throughout the UI\n" +
                            "- Removed \"Snapdragon Gallery\" as its replacement was already present in the previous version\n" +
                            "- General Improvements");
                    builder.setPositiveButton("Close", new Dialog.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1){
                            alertDialog4.dismiss();
                        }

                    });
                    builder.setNegativeButton("Visit our GitHub", new Dialog.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/SimplixDot"));
                            startActivity(browserIntent);
                            alertDialog4.dismiss();
                        }

                    });
                    alertDialog4 = builder.create();
                    alertDialog4.show();
                }


            }
        });

        ImageButton view_website = (ImageButton) findViewById(R.id.view_website);
        view_website.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://simplixdot.github.io"));
                startActivity(browserIntent);

            }


        });


        ImageButton channel_settings = (ImageButton) findViewById(R.id.settings);
        channel_settings.setOnClickListener(new View.OnClickListener() {

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
                        if (uchannel==0) uring="beta";
                        else if (uchannel==1) uring="stable";
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = builder.create();
                alertDialog1.show();
            }

        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ostget: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
                    final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
                    ImageView bgiv = (ImageView)findViewById(R.id.bgiv);
                    GaussianBlur.with(this).put(wallpaperDrawable, bgiv);

                }
                return;
            }

        }
    }


}
