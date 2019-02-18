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
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
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
import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
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
            "System Update", "Telegram Group",
            "Frequently Asked Questions", "About OS",
    };

    String[] subtitle = {
            "Check for updates", "Leave a feedback or request a feature",
            "Before you ask anything, check this list first", "Simplix One x Essential Feature Pack",
    };

    Integer[] imgid = {
            R.drawable.ic_system_update, R.drawable.ic_telegram_group,
            R.drawable.ic_faq, R.drawable.ic_about_os,
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

        TextView textView;
        textView = (TextView) findViewById(R.id.app_title);

        TextPaint paint = textView.getPaint();
        float width = paint.measureText(textView.getText().toString());

        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#C521FF"),
                        Color.parseColor("#4666FF"),
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);


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
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.simplixone.ota");
                    startActivity(intent);
                }

                else if(position == 1) {
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

                else if(position == 2) {
                    Intent intent = new Intent(MainActivity.this, faq.class);
                    startActivity(intent);
                }

                else if(position == 3) {
                    Intent intent = new Intent(MainActivity.this, About.class);
                    startActivity(intent);
                }


            }
        });



    }




}
