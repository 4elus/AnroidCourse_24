package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://www.omdbapi.com/?apikey=c47dffa7&s=batman";
        textView = findViewById(R.id.textview);
        imageView = findViewById(R.id.imageView);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET ,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       // textView.setText("Response: " + response.toString());

                        try {
                            JSONArray jsonArray = response.getJSONArray("Search");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String title = jsonObject.getString("Title");
                                String poster = jsonObject.getString("Poster");

                                Picasso picasso = new Picasso.Builder(getApplicationContext())
                                        .listener(new Picasso.Listener() {
                                            @Override
                                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

                                            }
                                        })
                                        .build();

                                picasso.load(poster)
                                        .into(imageView);

                                Toast.makeText(MainActivity.this, poster, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(jsonObjectRequest);

    }






















    public int percent(int number, int divide){
        int res = 0;
        int i = 1;
        int temp = 0;


        while (number > temp){
            temp = divide * i;
            i++;
            if (temp < number)
                res = temp;
        }

        return number - res;
    }
}