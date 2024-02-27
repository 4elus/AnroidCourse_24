package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView textView;
    int count = 0;
    int sum = 0;
    int price = 0;
    Spinner spinner;
    HashMap<String, Integer> items = new HashMap<>();

    TextView priceViewText;
    ImageView imageView;
    EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> list = new ArrayList<>();
        list.add("Cola");
        list.add("Burger");
        list.add("Combo");

        items.put("Cola", 500);
        items.put("Burger", 1000);
        items.put("Combo", 1500);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, list);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        textView = findViewById(R.id.qty_btn);

        priceViewText = findViewById(R.id.priceTxt);
        imageView = findViewById(R.id.image);
        nameEditText = findViewById(R.id.nameTxt);
    }

    public void sendToCart(View view) {
        Toast.makeText(this, "Hello, world", Toast.LENGTH_SHORT).show();
    }

    public void minus_btn(View view) {
        if(count >= 0){
            textView.setText(String.valueOf(count));
        }else{
            Toast.makeText(this, "Введите коректное значение", Toast.LENGTH_SHORT).show();
        }
        if(sum >= 0){
            sum = price * count;
            priceViewText.setText(sum + "$");
        }
        count--;
    }
    public void plus_btn(View view) {
        textView.setText(String.valueOf(count));

        sum = price * count;
        priceViewText.setText(sum + "$");
        count++;


        // textView работает только с String . С int  не работает

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // toString() так как до этого оно было листом
        String item = spinner.getSelectedItem().toString();
        price = items.get(item);
        priceViewText.setText(price + "$");

        if(i == 0){
            imageView.setImageResource(R.drawable.cola);
        }else if(i == 1){
            imageView.setImageResource(R.drawable.burger);
        }else if(i == 2){
            imageView.setImageResource(R.drawable.cimbo);
        }

        // проверка

        Toast.makeText(this, item + "цена" + nameEditText.getText(), Toast.LENGTH_SHORT).show();
    }

    // пока не нужен
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}