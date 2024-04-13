package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import Data.DbHandler;
import Model.Car;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHandler dbHandler = new DbHandler(this);

        dbHandler.addCar(new Car("Lexus", 3000));
        dbHandler.addCar(new Car("BMW", 4500));

        List<Car> carList = dbHandler.allCars();
    }
}