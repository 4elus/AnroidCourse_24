package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfirmOrderActivity extends AppCompatActivity {
    TextView nameTV;
    TextView priceTV;
    TextView orderName;
    TextView orderCount;

    ArrayList<String> newItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        newItems =  (ArrayList<String>)getIntent().getStringArrayListExtra("FILES_TO_SEND");

//        nameTV = findViewById(R.id.nameTxt);
//        priceTV = findViewById(R.id.priceTxt);
//        orderCount = findViewById(R.id.orderCount);
        orderName = findViewById(R.id.orderName);


        String title = "";
        String count = "";
        String sum = "";


        for (String elem : newItems){
            String[] str = elem.split("#");
            title += str[0] + " " + str[1] + " шт " + str[2] + " тг " + "\n";
//            count += str[1] + " шт" + "\n" ;
//            sum += str[2] + " тг"  +"\n";


            orderName.setText(title);
//            orderCount.setText(count);
//            priceTV.setText(sum);
        }

    }
    public void check(View view) {
    }
}