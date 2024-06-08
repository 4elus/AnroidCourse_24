package com.db.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    ChildEventListener messageChildEventListener;
    private ImageButton deleteBtn;
    ArrayList<Message> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message();
                message.setText(messageEditText.getText().toString());
                message.setName(userName);
                message.setImageurl(null);

                if(!messageEditText.getText().toString().isEmpty()){
                    myRef.push().setValue(message);
                }else{
                    sendMessageButton.isEnabled();
                    Toast.makeText(MainActivity.this, "нельзя отправить пустое соо", Toast.LENGTH_SHORT).show();
                }
            }
        });

        messageChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                messageAdapter.add(message);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef.addChildEventListener(messageChildEventListener);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("message").removeValue();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void init(){
        sendImageButton = findViewById(R.id.imgSend);
        sendMessageButton = findViewById(R.id.buttonSend);
        messageEditText = findViewById(R.id.messageEdit);
        listView = findViewById(R.id.list);
        deleteBtn = findViewById(R.id.deleteBtn);
        userName = "Default user";

        arrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, R.layout.message_item, arrayList);
        listView.setAdapter(messageAdapter);
    }

}