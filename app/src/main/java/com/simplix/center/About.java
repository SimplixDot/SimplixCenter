package com.simplix.center;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
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

        TextView textView;
        textView = (TextView) findViewById(R.id.team_title);

        TextPaint paint = textView.getPaint();
        float width = paint.measureText(textView.getText().toString());

        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#C521FF"),
                        Color.parseColor("#4666FF"),
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);

        textView = (TextView) findViewById(R.id.changelog2);

        paint = textView.getPaint();
        width = 600;

        textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#C521FF"),
                        Color.parseColor("#4666FF"),
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);

        textView = (TextView) findViewById(R.id.team_list);

        paint = textView.getPaint();
        width = paint.measureText(textView.getText().toString());

        textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#C521FF"),
                        Color.parseColor("#4666FF"),
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);

        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ImageButton github = (ImageButton) findViewById(R.id.github);
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/simplixone"));
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
                                    "+ Graphic Designer: Alex Besida \n" +
                                    "+ Check maintainers in our web!");

                            TextPaint paint = team.getPaint();

                            Shader textShader = new LinearGradient(0, 0, 600, team.getTextSize(),
                                    new int[]{
                                            Color.parseColor("#C521FF"),
                                            Color.parseColor("#4666FF"),
                                    }, null, Shader.TileMode.CLAMP);
                            team.getPaint().setShader(textShader);
                        } else {
                            TextView team = (TextView) findViewById(R.id.team_list);
                            team.setText(result1);


                            TextPaint paint = team.getPaint();

                            Shader textShader = new LinearGradient(0, 0, 600, team.getTextSize(),
                                    new int[]{
                                            Color.parseColor("#C521FF"),
                                            Color.parseColor("#4666FF"),
                                    }, null, Shader.TileMode.CLAMP);
                            team.getPaint().setShader(textShader);
                        }

                    }
                });

        TextView textView2 = (TextView) findViewById(R.id.about);

        paint = textView2.getPaint();
        width = paint.measureText("About");

        textShader = new LinearGradient(0, 0, width, textView2.getTextSize(),
                new int[]{
                        Color.parseColor("#C521FF"),
                        Color.parseColor("#4666FF"),
                }, null, Shader.TileMode.CLAMP);
        textView2.getPaint().setShader(textShader);
    }

}
