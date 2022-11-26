package com.example.counterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    Button upplus, upminus,lowminus,lowplus;
    TextView lowvalue, upvalue;
    CheckBox lowVib, upVib, lowSound, upSound;

    SettingsClass settingsClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        upplus = (Button) findViewById(R.id.button_upplus);
        upminus = (Button) findViewById(R.id.button_upminus);
        lowminus = (Button) findViewById(R.id.button_lowminus);
        lowplus = (Button) findViewById(R.id.button_lowplus);
        lowvalue = (TextView) findViewById(R.id.tv_lowvalue);
        upvalue = (TextView) findViewById(R.id.tv_upvalue);

        lowVib = (CheckBox) findViewById(R.id.cb_lowervib);
        lowSound = (CheckBox) findViewById(R.id.cb_lowersound);
        upVib = (CheckBox) findViewById(R.id.cb_upvib);
        upSound = (CheckBox) findViewById(R.id.cb_upsound);


        settingsClass = SettingsClass.getSettingsClass(getApplicationContext());


        upplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsClass.upperLimit++;
                upvalue.setText(String.valueOf(settingsClass.upperLimit));
            }
        });
        upminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsClass.upperLimit--;
                upvalue.setText(String.valueOf(settingsClass.upperLimit));
            }
        });
        lowplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsClass.lowerLimit++;
                lowvalue.setText(String.valueOf(settingsClass.lowerLimit));
            }
        });
        lowminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsClass.lowerLimit--;
                lowvalue.setText(String.valueOf(settingsClass.lowerLimit));
            }
        });

        lowVib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsClass.lowerVib=b;
            }
        });
        lowSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsClass.lowerSound=b;
            }
        });
        upSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsClass.upperSound=b;
            }
        });
        upVib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsClass.upperVib=b;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        upvalue.setText(String.valueOf(settingsClass.upperLimit));
        lowvalue.setText(String.valueOf(settingsClass.lowerLimit));
        lowVib.setChecked(settingsClass.lowerVib);
        lowSound.setChecked(settingsClass.lowerSound);
        upSound.setChecked(settingsClass.upperSound);
        upVib.setChecked(settingsClass.upperVib);
    }
    @Override
    protected void onPause() {
        super.onPause();
        settingsClass.saveValues();
    }
}