package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmOrderActivity extends AppCompatActivity {
    TextView nameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        nameTV = findViewById(R.id.nameTxt);

        Bundle arguments = getIntent().getExtras();
        String name = arguments.get("name").toString();    // Hello World


        nameTV.setText(name);
    }

    public void check(View view) {
        Bundle arguments = getIntent().getExtras();
        String name2 = arguments.get("name").toString();    // Hello World
        Toast.makeText(this, "name: " + name2, Toast.LENGTH_SHORT).show();
    }
}