package com.simplix.center;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jaredrummler.android.device.DeviceName;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class About extends AppCompatActivity {
    TextView team;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_about);


        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ImageButton web = (ImageButton) findViewById(R.id.web);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://simplixdot.github.io/"));
                startActivity(browserIntent);
            }
        });
        ImageButton github = (ImageButton) findViewById(R.id.github);
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SimplixDot"));
                startActivity(browserIntent);
            }
        });
        Ion.with(getApplicationContext())
                .load("https://raw.githubusercontent.com/SimplixDot/platform_vendor_ota/pie/team.txt")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result1) {
                        if (e != null || result1.toLowerCase().contains("fail")) {
                            TextView team = (TextView) findViewById(R.id.team_list);
                            team.setText("+ Co-Founder / Lead Developer: Nicola Nicolov \n" +
                                    "+ Co-Founder / Lead Developer: Yordan Stoychev \n" +
                                    "+ Developer: Damyan Slavov\n" +
                                    "+ Developer: Pratik Pithore\n" +
                                    "+ Graphic Designer: Mkenzo_8 \n" +
                                    "+ Check maintainers in our web!");
                        } else {
                            TextView team = (TextView) findViewById(R.id.team_list);
                            team.setText(result1);
                        }

                    }
                });
    }

}
