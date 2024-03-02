package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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
    String item;

    HashMap<String, Integer> items = new HashMap<>();
    ArrayList<String> newItems = new ArrayList<>();


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
        if(count > 0){
            newItems.add(item+"#"+count+"#"+sum);
        }
    }
    public void confirm(View view) {
        Intent intent = new Intent(MainActivity.this, ConfirmOrderActivity.class);
        intent.putExtra("FILES_TO_SEND", newItems);
        startActivity(intent);

    }

    public void plusBtn(View view) {
        sum = price * count;
        priceTV.setText(sum + "$");
        textView.setText(String.valueOf(count));
        count++;

    }

    public void minusBtn(View view) {
        if(count > 0){
            count--;
            sum = price * count;
            priceTV.setText(sum + "$");
            textView.setText(String.valueOf(count));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        reset();
        item = spinner.getSelectedItem().toString();
        price = items.get(item);
        if (i == 0) {
            itemIV.setImageResource(R.drawable.cola);
        } else if (i == 1) {
            itemIV.setImageResource(R.drawable.burger);
        }else{
            itemIV.setImageResource(R.drawable.combo);
        }
    }
    private void reset(){
        sum = 0;
        count = 0;
        priceTV.setText(sum + "$");
        textView.setText(String.valueOf(count));
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}