package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Data.DbHandler;
import Model.Car;

public class AddCarActivity extends AppCompatActivity {

    EditText nameET;
    EditText priceET;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        nameET = findViewById(R.id.nameET);
        priceET = findViewById(R.id.priceET);
        saveBtn = findViewById(R.id.addBtn);



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHandler dbHandler = new DbHandler(getApplicationContext());

                String name = nameET.getText().toString();
                double price = Double.parseDouble(priceET.getText().toString());

                dbHandler.addCar(new Car(name, price));
                Toast.makeText(AddCarActivity.this, "Car added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}