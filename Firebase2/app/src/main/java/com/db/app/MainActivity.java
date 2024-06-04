package com.db.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MessageAdapter messageAdapter;
    private ListView listView;
    private ImageButton sendImageButton;
    private Button sendMessageButton;
    private EditText messageEditText;
    private String userName;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        ArrayList<Message> arrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, R.layout.message_item, arrayList);
        listView.setAdapter(messageAdapter);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  

            }
        });
    }


    public void init(){
        sendImageButton = findViewById(R.id.imgSend);
        sendMessageButton = findViewById(R.id.buttonSend);
        messageEditText = findViewById(R.id.messageEdit);
        listView = findViewById(R.id.list);
        userName = "Default user";
    }

}