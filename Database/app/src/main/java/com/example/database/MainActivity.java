package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Data.DbHandler;
import Model.Car;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
                startActivity(intent);
            }
        });

    }

    public void output(View view) {
        DbHandler dbHandler = new DbHandler(this);

        List<Car> list = dbHandler.allCars();

        for (Car car: list) {
            Log.i("Car info", car.getName() + " : " + car.getPrice());
        }
    }
}