package com.example.garysandroidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
//import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
//import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garysandroidlabs.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    //private MainViewModel model;

    private String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "In OnCreate()");

        //view binding
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        //instaniate a view model
        //model = new ViewModelProvider(this).get(MainViewModel.class);
        variableBinding.emailPrompt.setText(prefs.getString("Email", ""));
        //event listener
        variableBinding.loginBtn.setOnClickListener((v)->{
            Log.e(TAG, "You clicked the button");

            //create next page
            Intent nextPage = new Intent(this, SecondActivity.class);

            //get email and pw from user prompt
            String emailAddress = variableBinding.emailPrompt.getText().toString();
            String pw = variableBinding.pwPrompt.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Email", emailAddress);
            editor.apply();

//            nextPage.putExtra("Email", emailAddress);
//            nextPage.putExtra("Password", pw);

            //goto next page
            startActivity(nextPage);
        });

    }
}