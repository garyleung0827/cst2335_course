package com.example.garysandroidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garysandroidlabs.databinding.ActivityMainBinding;

/**
 * The main activity java class of the activity_main.xml
 * @author Kin Man Leung
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding variableBinding;
//    private MainViewModel model;

    /**
     * A function to check the password contains at least an uppercase, a lowercase,
     * a number and a special character.
     * @param pw The String object that we are checking
     * @return  Return true if the password is complex enough
     */
    private boolean checkPasswordComplexity(String pw){
        if(!pw.matches(".*[A-Z].*")){
            Toast.makeText(this, "missing an upper case letter", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!pw.matches(".*[a-z].*")){
            Toast.makeText(this, "missing a lower case letter", Toast.LENGTH_SHORT);
            return false;
        }
        else if(!pw.matches(".*\\d.*")){
            Toast.makeText(this, "missing a number", Toast.LENGTH_SHORT);
            return false;
        }
        else if(!pw.matches(".*[#$%^&*!@?].*")){
            Toast.makeText(this, "missing a Special Character (#$%^&*!@?)", Toast.LENGTH_SHORT);
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view binding
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        /** This holds the text at the centre of the screen*/
        TextView tV = variableBinding.textView;
        /** This holds the login in button*/
        Button loginBtn = variableBinding.loginBtn;
        /** This hold the input of the password from the user */
        EditText eT = variableBinding.editText;

        //click button to show text from edittext to text view
        loginBtn.setOnClickListener(click -> {
            String passwd = eT.getText().toString();
            if(checkPasswordComplexity(passwd)){
                tV.setText("Your password is complex enough");
            }
            else{
                tV.setText("You shall not pass!");
            }
        });
    }
}


