package com.example.counterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button minus, plus, settings;
    TextView value;

    Vibrator vibrator;
    MediaPlayer mediaPlayer;

    SettingsClass settingsClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        minus = (Button) findViewById(R.id.button_minus);
        plus = (Button) findViewById(R.id.button_plus);
        value = (TextView) findViewById(R.id.tv_value);
        settings = (Button) findViewById(R.id.button_settings);

        Context context = getApplicationContext();
        settingsClass = SettingsClass.getSettingsClass(context);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(context, R.raw.beep);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValue(1);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValue(-1);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });
    }

    public void updateValue(int step) {
        if (step < 0) {
            if (settingsClass.currentValue + step < settingsClass.lowerLimit) {
                settingsClass.currentValue = settingsClass.lowerLimit;
                if (settingsClass.lowerVib) {
                    alertVibrate();
                }
                if (settingsClass.lowerSound) {
                    alertSound();
                }
            } else settingsClass.currentValue += step;
        }
        if (step > 0) {
            if (settingsClass.currentValue + step > settingsClass.upperLimit) {
                settingsClass.currentValue = settingsClass.upperLimit;
                if (settingsClass.upperVib) {
                    alertVibrate();
                }
                if (settingsClass.upperSound) {
                    alertSound();
                }
            } else settingsClass.currentValue += step;
        }
        value.setText(String.valueOf(settingsClass.currentValue));
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingsClass.loadValues();
        value.setText(String.valueOf(settingsClass.currentValue));

    }

    @Override
    protected void onPause() {
        super.onPause();
        settingsClass.saveValues();
    }

    public void alertSound() {
        mediaPlayer.seekTo(0);
        mediaPlayer.start();

    }

    public void alertVibrate() {
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else vibrator.vibrate(500);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch(keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if(action == KeyEvent.ACTION_DOWN){
                    updateValue(-5);
                }return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if(action == KeyEvent.ACTION_UP){
                    updateValue(5);
                }return true;
        }
        return super.dispatchKeyEvent(event);
    }
}











