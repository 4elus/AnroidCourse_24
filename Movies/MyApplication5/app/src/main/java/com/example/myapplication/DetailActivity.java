package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    TextView titleTV;
    TextView releaseTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        titleTV = findViewById(R.id.titleTV);
        releaseTV = findViewById(R.id.releaseTV);

        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("film").toString();
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();


        String url = "https://www.omdbapi.com/?apikey=c47dffa7&i="+id;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET ,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // textView.setText("Response: " + response.toString());

                        try {
                            String title = response.getString("Title");
                            Toast.makeText(DetailActivity.this, title, Toast.LENGTH_SHORT).show();




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