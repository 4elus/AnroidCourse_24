package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable runnable;
    TextView textView;
    int count = 0;
    SharedPreferences settings;
    int number = 90;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences("Account", MODE_PRIVATE);
        textView = findViewById(R.id.text_view);
        Log.i("Lifecycle", "OnCreate");
       // textView.append("OnCreate()" + "\n");
    }


    private void convertToMinutes(int number){
        int min = number / 60;
        int sec = number - (min * 60);

        textView.setText("Время: " + min+":"+sec);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle", "OnStart");
        getNumber();
        //textView.append("OnStart()" + "\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Lifecycle", "onResume");
       // textView.append("onResume()" + "\n");

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, 300);
                convertToMinutes(number);
                number--;
            }
        }, 300);



    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Lifecycle", "onPause");
        //textView.append("onPause()" + "\n");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Lifecycle", "onPause");
        //textView.append("onStop()" + "\n");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Lifecycle", "onRestart");
        //textView.append("onRestart()" + "\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy");
        //textView.append("onDestroy()" + "\n");
    }

    public void plus(View view) {
        count += 1;
        textView.setText("Счетчик: " + count);
        SharedPreferences.Editor prefEdit = settings.edit();
        prefEdit.putInt("Number", count);
        prefEdit.apply();
    }

    public void getNumber(){
        count = settings.getInt("Number", 0);
    }
}