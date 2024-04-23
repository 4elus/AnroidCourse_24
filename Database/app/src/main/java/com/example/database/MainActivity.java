package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        ListView listView = findViewById(R.id.list_view);

        DbHandler dbHandler = new DbHandler(this);

        List<Car> list = dbHandler.allCars();
        List<String> stringList = new ArrayList<String>();

        for (Car car: list) {
            String data = car.getId() + "#" + car.getName() + " : " + car.getPrice() + "$";

            stringList.add(data);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringList);
        listView.setAdapter(adapter);

        floatingActionButton = findViewById(R.id.floatingActionButton);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] id_dataCar = stringList.get(i).split("#");
                int id = Integer.parseInt(id_dataCar[0]);
                String dataCar = id_dataCar[1];

                Toast.makeText(MainActivity.this, "Id: " + id + "DataCar: " + dataCar, Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
                startActivity(intent);
            }
        });

    }
}