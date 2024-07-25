package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BuyItemActivity extends AppCompatActivity {
    TextView orderList;
    String showList;
    OrderDao orderDao;
    List<Order> order;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_item);

        orderDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DbConfig.ROOM_DB_NAME)
                .build()
                .orderDao();
        orderList = findViewById(R.id.order);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                order = orderDao.getOrdersByEmailo(ConfigUser.EMAIL_USER);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Order elem: order) {
                            showList += elem.item_name + " " + elem.price + " x " + elem.item_count + " шт " +
                                    " Итого: " + elem.item_count * elem.price + " тг " +"\n";
                        }
                        orderList.setText(showList);
                    }
                });
            }
        });
    }
    public void taptomaina(View view) {
        Intent intent = new Intent(BuyItemActivity.this, MainActivity.class);
        startActivity(intent);
    }
}