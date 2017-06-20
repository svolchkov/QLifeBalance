package com.example.sergeyv.qlifebalance;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    String[] quotes;
    ImageView mainImage;
    ImageView scaleImage;
    TextView quoteText;
    SeekBar seekBar;
    int progress = 0;
    public static String MY_PREFS_NAME = "com.example.sergeyv.qlifebalance.prefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // retrieves progress bar setting from preferences
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (prefs != null) {
            progress = prefs.getInt("progress", 0);//"No name defined" is the default value.
        }

        mainImage = (ImageView) findViewById(R.id.imageMain);
        quoteText = (TextView)findViewById(R.id.quoteText);
        scaleImage = (ImageView) findViewById(R.id.imageScales);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        quotes = getResources().getStringArray(R.array.quotes);
        // sets images, seekbar progress and quote
        setImagesAndQuote(progress);
        seekBar.setProgress(progress);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                setImagesAndQuote(progress);
             }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
             }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
             }
        });
    }

    private void setImagesAndQuote(int progress){
        // sets images and quote
        String imageName = "image_" + progress;
        int resId = MainActivity.this.getResources().getIdentifier(imageName, "drawable", MainActivity.this.getPackageName());
        mainImage.setImageResource(resId);

        String scalesImageName = "scales" + progress;
        resId = MainActivity.this.getResources().getIdentifier(scalesImageName, "drawable", MainActivity.this.getPackageName());
        scaleImage.setImageResource(resId);

        quoteText.setText(quotes[progress]);
    }

    @Override
    protected void onStop(){
        // save current progress value to preferences
        super.onStop();
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("progress", progress);
        editor.apply();
    }
}
