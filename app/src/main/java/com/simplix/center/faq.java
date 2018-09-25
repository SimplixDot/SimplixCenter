package com.simplix.center;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class faq extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_faq);
        Ion.with(getApplicationContext())
                .load("https://raw.githubusercontent.com/SimplixDot/platform_vendor_ota/pie/faq.txt")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result1) {
                        if (e != null || result1.toLowerCase().contains("fail")) {
                            TextView faq = (TextView) findViewById(R.id.faq);
                            faq.setText("Q: How to report a bug?\n" +
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
                        } else {
                            TextView faq = (TextView) findViewById(R.id.faq);
                            faq.setText(result1);
                        }

                    }
                });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.back);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
    }

}
