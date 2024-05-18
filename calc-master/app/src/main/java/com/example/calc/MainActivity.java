package com.example.calc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    String currentText  = "";
    float result = 0;
    String operator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
    }
    public void number(View view) {
        Button button = findViewById(view.getId());
        currentText += button.getText().toString();
        textView.setText(String.valueOf(currentText));

    }
    public void OperatorBtn(View view) {
        if(currentText == ""){
            currentText = "0.";
        }

        if(!currentText.contains(".")){
            currentText += ".";
        }
        textView.setText(String.valueOf(currentText));

    }
    public void operator(View view) {
        Button button = findViewById(view.getId());
        operator = button.getText().toString();
        result = Float.parseFloat(currentText);

        //  чтобы сбросить предыдущее число а result чтобы сохранить
        currentText = "";
    }
    public void equal(View view) {
        float number = Float.parseFloat(currentText);
        boolean key = true;
        // equals сравивает содержимое обьектов
        if(operator.equals("+")){
            result += number;
        }else if(operator.equals("-")){
            result -= number;
        }else if(operator.equals("*")){
            result *= number;
        }else if(operator.equals("/")){
            if(number != 0){
                result /= number;
            }else{
                // тут кстати текст не выводит после того как делю на 0
                textView.setText("ошибка , на ноль делить нельзя");
                key = false;
            }
        }else if(operator.equals("%")){
            result = percent((int) result, (int) number);
            //
        }else if(operator.equals(".")){

        }
        currentText = String.valueOf(result);
        if(key != false){
            textView.setText(currentText);
        }
    }
    public void clear(View view) {
        currentText = "";
        result = 0;
        textView.setText("");
    }
    public int percent(int a, int b){
        int res = 0;
        int temp = 0;
        int i = 1;
        while(a > temp){
            temp = b * i;
            i++;
            if(temp < a){
                res = temp;
            }
        }
        return a - res;
    }
}