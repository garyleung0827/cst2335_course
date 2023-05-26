package com.example.garysandroidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
//import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
//import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.example.garysandroidlabs.databinding.ActivityMainBinding;


import com.example.garysandroidlabs.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;

    ImageView imgView;
    Switch sw;

    Button languageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        //imgView = findViewById(R.id.flagView);
        //sw = findViewById(R.id.switch1);


        imgView = variableBinding.flagView;
        sw = variableBinding.switch1;
        languageButton = variableBinding.languageButton;

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLanguage();
                }
        });



        sw.setOnCheckedChangeListener( (btn, isChecked) -> {
            if (isChecked)
            {
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(5000);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());

                imgView.startAnimation(rotate);
            }
            else {
                imgView.clearAnimation();
            }
        });
    }

    private void switchLanguage() {
        // Perform actions to switch the language
        // For example, you can change the locale using the following code:
        Configuration config = getResources().getConfiguration();
        config.setLocale(new Locale("zh", "HK")); // Switch
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Restart the activity to apply the language change
        recreate();
    }
}