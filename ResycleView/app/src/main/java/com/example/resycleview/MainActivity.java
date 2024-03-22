package com.example.resycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycleView);


        ArrayList<MyRecycleView> arrayList = new ArrayList<>();
        arrayList.add(new MyRecycleView(R.drawable.baseline_1k_24, "Title 1"));
        arrayList.add(new MyRecycleView(R.drawable.baseline_add_circle_24, "Title 2"));
        arrayList.add(new MyRecycleView(R.drawable.baseline_add_reaction_24, "Title 3"));



        recyclerView.setHasFixedSize(true);
        MyRecycleViewAdapter adapter = new MyRecycleViewAdapter(arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }
}