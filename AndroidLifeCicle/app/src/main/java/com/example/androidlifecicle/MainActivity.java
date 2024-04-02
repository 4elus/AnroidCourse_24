package com.example.androidlifecicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable runnable;
    TextView textView;
    SharedPreferences settings;
    int number = 90;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences("Account", MODE_PRIVATE);

        textView = findViewById(R.id.text_view);

        getNumber();
    }

    private void convertToMinutes(int number){
        int min = number / 60;
        int sec = number - (min * 60);
        if(number >= 0){
            textView.setText("Время: " + min+":"+sec);
        }
    }
    public void plus(View view) {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                SharedPreferences.Editor prefEdit = settings.edit();
                prefEdit.putInt("Number", number);
                prefEdit.apply();

                handler.postDelayed(runnable, 1000);
                convertToMinutes(number);
                number--;
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getNumber(){
        number = settings.getInt("Number", 0);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void newPage(View view) {
        Intent intent = new Intent();

    }
}