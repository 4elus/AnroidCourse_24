package com.example.db_task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Data.DbHandler;
import Model.doList;

public class addList extends AppCompatActivity {

    EditText title;
    EditText desc;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);

        button = findViewById(R.id.addToListBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHandler dbHandler = new DbHandler(getApplicationContext());

                String mTitle = title.getText().toString();
                String mDesc = desc.getText().toString();

                dbHandler.addList(new doList(mTitle, mDesc));

                Intent intent = new Intent(addList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}