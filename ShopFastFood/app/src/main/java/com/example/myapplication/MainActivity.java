package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nameET;
    TextView textView;
    TextView priceTV;
    ImageView itemIV;
    Spinner spinner;
    int count = 0;
    int sum = 0;
    int price = 0;
    HashMap<String, Integer> items = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameET = findViewById(R.id.nameTxt);
        textView = findViewById(R.id.qtyBtn);
        itemIV = findViewById(R.id.itemIV);
        priceTV = findViewById(R.id.priceTxt);
        ArrayList<String> list = new ArrayList<>();
        list.add("Cola");
        list.add("Burger");
        list.add("Combo");
        
        items.put("Cola", 500);
        items.put("Burger", 1000);
        items.put("Combo", 1500);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    public void sentToCart(View view) {
        Toast.makeText(this, "Hello world", Toast.LENGTH_SHORT).show();
        Intent newIntent = new Intent(MainActivity.this, ConfirmOrderActivity.class);
        newIntent.putExtra("name", nameET.getText()); //Optional parameters
        startActivity(newIntent);
    }

    public void plusBtn(View view) {


        count+=1;
        sum = price * count;
        priceTV.setText(sum + "$");

        textView.setText(String.valueOf(count));
    }

    public void minusBtn(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = spinner.getSelectedItem().toString();
        price = items.get(item);
        if (i == 0) {
            itemIV.setImageResource(R.drawable.cola);
        } else if (i == 1) {
            itemIV.setImageResource(R.drawable.burger);
        }else{
            itemIV.setImageResource(R.drawable.combo);
        }

        Toast.makeText(this, item + " цена: " + nameET.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}