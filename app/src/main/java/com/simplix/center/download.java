package com.simplix.center;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jaredrummler.android.device.DeviceName;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class download extends AppCompatActivity {
    TextView text1;
    TextView text2;
    TextView text3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_download);

        Intent intent = getIntent();
        String version = intent.getStringExtra("version");
        String uring = intent.getStringExtra("uring");
        String device = intent.getStringExtra("device");
        String update = intent.getStringExtra("update");
        final String dwd = intent.getStringExtra("dwd");

        TextView text1 = (TextView) findViewById(R.id.version);
        text1.setText("Your version: "+version);
        TextView text2 = (TextView) findViewById(R.id.device);
        text2.setText("Device: "+device);
        TextView text3 = (TextView) findViewById(R.id.update);
        text3.setText("Update version: "+update);

        Ion.with(getApplicationContext())
                .load("https://raw.githubusercontent.com/SimplixDot/platform_vendor_ota/pie/"+DeviceName.getDeviceInfo(download.this).codename+"/"+"changelog-"+uring+".txt")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result1) {
                        TextView text3 = (TextView) findViewById(R.id.changelog);
                        text3.setText(result1);

                    }
                });
        FloatingActionButton download = (FloatingActionButton) findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dwd));
                startActivity(browserIntent);

            }


        });
        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                onBackPressed();

            }


        });
    }

}
