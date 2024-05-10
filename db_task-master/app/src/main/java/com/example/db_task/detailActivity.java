package com.example.db_task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Data.DbHandler;
import Model.doList;

public class detailActivity extends AppCompatActivity {
    EditText title;
    EditText desc;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = findViewById(R.id.titleET);
        desc = findViewById(R.id.descET);
        saveBtn = findViewById(R.id.saveBtn);

        int id = getIntent().getIntExtra("id", 0);
        DbHandler dbHandler = new DbHandler(this);
        doList doList = dbHandler.getList(id);

        title.setText(doList.getTitle());
        desc.setText(doList.getDescription());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = title.getText().toString();
                String Description = desc.getText().toString();

                doList.setTitle(Title);
                doList.setDescription(Description);

                dbHandler.updateList(doList);

                Intent intent = new Intent(detailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}