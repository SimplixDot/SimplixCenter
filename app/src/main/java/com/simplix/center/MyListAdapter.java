package com.simplix.center;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final Integer[] imgid;

    public MyListAdapter(Activity context, String[] maintitle, String[] subtitle, Integer[] imgid) {
        super(context, R.layout.list_layout, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.imgid=imgid;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_layout, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.textView);

        TextPaint paint = titleText.getPaint();
        float width = paint.measureText(titleText.getText().toString());

        Shader textShader = new LinearGradient(0, 0, width, titleText.getTextSize(),
                new int[]{
                        Color.parseColor("#C521FF"),
                        Color.parseColor("#4666FF"),
                }, null, Shader.TileMode.CLAMP);
        titleText.getPaint().setShader(textShader);

        paint = subtitleText.getPaint();
        width = paint.measureText(subtitleText.getText().toString());

        textShader = new LinearGradient(0, 0, width, subtitleText.getTextSize(),
                new int[]{
                        Color.parseColor("#C521FF"),
                        Color.parseColor("#4666FF"),
                }, null, Shader.TileMode.CLAMP);
        subtitleText.getPaint().setShader(textShader);

        titleText.setText(maintitle[position]);
        imageView.setImageResource(imgid[position]);
        subtitleText.setText(subtitle[position]);

        return rowView;

    };
}
