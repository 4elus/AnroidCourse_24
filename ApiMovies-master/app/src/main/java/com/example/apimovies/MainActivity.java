package com.example.apimovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;


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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Movies> movies = new ArrayList<>();
    MovieAdapter movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycleView);

        ArrayList<Movies> arrayList = new ArrayList<>();

         recyclerView.hasFixedSize();
//        MovieAdapter adapter = new MovieAdapter(arrayList);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(layoutManager);


        String url = "https://www.omdbapi.com/?apikey=c47dffa7&s=batman";
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
                                String year = jsonObject.getString("Year");
                                String type = jsonObject.getString("Type");

                                Movies movie = new Movies();
                                movie.setTitle(title);
                                movie.setPoster(poster);
                                movie.setYear(year);
                                movie.setType(type);

                                movies.add(movie);
                            }
                            movieAdapter = new MovieAdapter(MainActivity.this, movies);
                            recyclerView.setAdapter(movieAdapter);

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
}