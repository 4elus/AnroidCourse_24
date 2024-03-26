package com.example.materialdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Пицца");
        arrayList.add("Кола");
        arrayList.add("Фри");

        ArrayList<MyItem> items = new ArrayList<>();
        items.add(new MyItem("Кола", R.drawable.cola, 15, "COLA"));
        items.add(new MyItem("Фри", R.drawable.cola, 20, "BURGER"));
        items.add(new MyItem("Пицца", R.drawable.cola, 25, "PIZZA"));


        //this или getApplication
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayList);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String title = arrayList.get(position);
                Intent itemActivity = new Intent(MainActivity.this, ItemActivity.class);
                for (MyItem item: items) {
                    if(item.getTitle() == title){
                        itemActivity.putExtra("title", item.getTitle());
                        itemActivity.putExtra("image", item.getImg());
                        itemActivity.putExtra("price", item.getPrice());
                        itemActivity.putExtra("desc", item.getDesc());


                    }
                }
                startActivity(itemActivity);
            }
        });
    }
}