package com.example.garysandroidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.garysandroidlabs.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * The main activity java class of the activity_main.xml
 * @author Kin Man Leung
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    protected String cityName;
    protected RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());

        binding.forecastBtn.setOnClickListener(click -> {
            cityName = binding.cityTextField.getText().toString();
            String stringURL = null;
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName, "UTF-8") + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            //this goes in the button click handler:
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response)->{
                    try {
                        JSONArray weather = response.getJSONArray("weather");
                        JSONObject data = response.getJSONObject("main");
                        binding.temp.setText("The current temperature is " + data.getString("temp"));
                        binding.maxTemp.setText("The max temperature is " + data.getString("temp_max"));
                        binding.minTemp.setText("The min temperature is " + data.getString("temp_min"));
                        binding.humitidy.setText("The humidity is " + data.getString("humidity") + "%");
                        binding.description.setText(weather.getJSONObject(0).getString("description"));
                        String iconName = weather.getJSONObject(0).getString("icon");

                        String imageUrl = "https://openweathermap.org/img/w/" + iconName + ".png";

                        String pathname = getFilesDir() + "/" + iconName + ".png";
                        File f = new File(pathname);
                        if(f.exists()){
                            binding.icon.setImageBitmap(BitmapFactory.decodeFile(pathname));
                        }
                        else {
                            ImageRequest imgReq = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    binding.icon.setImageBitmap(bitmap);
                                }
                            }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {
                                int j = 0;
                            });
                            queue.add(imgReq);
                        }
                    }
                    catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    },
                    (error) ->
                    {  int j =0;   }
            );

            queue.add(request);



        });
    }
}


