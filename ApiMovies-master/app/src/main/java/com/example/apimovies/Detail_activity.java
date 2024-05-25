package com.example.apimovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Detail_activity extends AppCompatActivity {
    TextView title;
    TextView year;
    ImageView poster;
    TextView genre;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = findViewById(R.id.titleView);
        year = findViewById(R.id.yearView);
        poster = findViewById(R.id.imageView);
        genre = findViewById(R.id.genreView);
        description = findViewById(R.id.descirptionView);



        String url = "https://www.omdbapi.com/?apikey=c47dffa7&i=tt2975590";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET ,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // textView.setText("Response: " + response.toString());
                        try {
                            // search нету
                            JSONArray jsonArray = response.getJSONArray("Search");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String Title = jsonObject.getString("Title");
                                String Poster = jsonObject.getString("Poster");
                                String Released = jsonObject.getString("Released");
                                String Genre = jsonObject.getString("Genre");
                                String Plot = jsonObject.getString("Plot");

                                title.setText(Title);
                                year.setText(Released);
                                title.setText(Genre);
                                description.setText(Plot);

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
}