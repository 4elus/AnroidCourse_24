package com.alexander.roomdbproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.helloUser);
        userDao = Room.databaseBuilder(this,
                        AppDatabase.class, DbConfig.ROOM_DB_NAME)
                //.addMigrations(AppDatabase.MIGRATION_1_2)  // Add your migration(s)
                //.fallbackToDestructiveMigration()
                .build().userDao();

        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("Id").toString();

        textView.setText("Hello, " + id.toString());


        // УДАлИТь

        //fastfoodAdapter = new ItemRecyclerView(userDao.getAllUsers()); // Возвращает список всех данных из таблицы!

    }
}