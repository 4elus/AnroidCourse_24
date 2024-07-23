package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MoreActivity extends AppCompatActivity {
    String itemName;
    ImageView imageView;
    TextView title;
    TextView price;
    TextView description;
    ItemDao itemDao;
    Item item;
    int sum;
    int count;
    TextView itemCount;
    ArrayList<Item> addItems = new ArrayList<>();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        init();
        itemName = getIntent().getStringExtra("itemName" );
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                item = itemDao.getItemByTitle(itemName);

                title.setText(item.getName());
                description.setText(item.getDescription());
                price.setText(String.valueOf(item.getPrice()));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Glide.with(MoreActivity.this)
                .load(item.getImage())
                .into(imageView);
    }
    public void init() {
        imageView = findViewById(R.id.moreImg);
        title = findViewById(R.id.titleMore);
        price = findViewById(R.id.priceMore);
        description = findViewById(R.id.descMore);
        itemCount = findViewById(R.id.itemCount);
        itemDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DbConfig.ROOM_DB_NAME)
                .build()
                .itemDao();
    }

    public void plus(View view) {
        count = Integer.parseInt((String) itemCount.getText());

        sum = Integer.parseInt((String) price.getText()) + item.price;
        count++;

        price.setText(String.valueOf(sum));
        itemCount.setText(String.valueOf(count));
    }

    public void minus(View view) {
        count = Integer.parseInt((String) itemCount.getText());
        sum = Integer.parseInt((String) price.getText()) - item.price;
        count--;
        if(count >= 0){
            price.setText(String.valueOf(sum));
            itemCount.setText(String.valueOf(count));
        }
    }

    public void buyNow(View view) {
        // с циклом
//        addItems.add();
    }
}