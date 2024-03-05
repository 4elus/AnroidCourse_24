package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String playerSymbol;
    String[][] matrix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerSymbol = "X";
        matrix = new String[3][3];
    }

    public void buttonClicked(View view){
        Button button = findViewById(view.getId());
        button.setText(playerSymbol);
        String tag = button.getTag().toString();
        String[] numbers = tag.split("#");
        int row = Integer.parseInt(numbers[0]);
        int col = Integer.parseInt(numbers[1]);

        switchPlayer();
        Toast.makeText(this, row + " " + col, Toast.LENGTH_SHORT).show();
    }


    public void switchPlayer(){
        if (playerSymbol == "X")
            playerSymbol = "O";
        else
            playerSymbol = "X";
    }
}