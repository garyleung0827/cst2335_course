package com.example.garysandroidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
//import android.content.DialogInterface;
import android.os.Bundle;
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
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view binding
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        //instaniate a view model
        model = new ViewModelProvider(this).get(MainViewModel.class);

        TextView mytext = variableBinding.textView;
        Button mybutton = variableBinding.mybutton;
        EditText myedit = variableBinding.editText;
        Switch myswitch = variableBinding.mySwitch;
        CheckBox mycheckbox = variableBinding.checkBox;
        RadioButton myradiobutton = variableBinding.radioButton;
        ImageButton myimagebutton = variableBinding.imageButton;
        //mageView myimageview = variableBinding.imageButton;

        //click button to show text from edittext to text view
        mybutton.setOnClickListener(click -> {
            model.editString.postValue(myedit.getText().toString());
            model.editString.observe(this, s ->
                mytext.setText("Your edit text has " + s)
            );
        });

        //button view model
        model.isSelected.observe(this, selected -> {
            mycheckbox.setChecked(selected);
            myradiobutton.setChecked(selected);
            myswitch.setChecked(selected);

            Context context = getApplicationContext();
            CharSequence text = "Selected!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

        //compound button listener
        mycheckbox.setOnCheckedChangeListener((btn, isChecked) ->
            model.isSelected.postValue(isChecked)
        );
        myradiobutton.setOnCheckedChangeListener((btn, isChecked) ->
            model.isSelected.postValue(isChecked)
        );
        myswitch.setOnCheckedChangeListener((btn, isChecked) ->
            model.isSelected.postValue(isChecked)
        );

        //click imagebutton to show the toast image
        myimagebutton.setOnClickListener(click -> {
            int width = myimagebutton.getWidth();
            int height = myimagebutton.getHeight();

            Context context = getApplicationContext();
            CharSequence text = "The width = " + width + " and height = " + height;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
        //
    }
}