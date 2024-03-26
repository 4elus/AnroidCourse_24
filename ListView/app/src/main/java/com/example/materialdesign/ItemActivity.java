package com.example.materialdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);


        Intent intent = getIntent();
        String name = intent.getStringExtra("title");
        int img = intent.getIntExtra("image", 0);
        String desc = intent.getStringExtra("desc");
        int price = intent.getIntExtra("price", 0);



        Toast.makeText(this, "extra: " + name + " " + price + " " + desc + " " + img, Toast.LENGTH_SHORT).show();
    }
}