package com.alisherzhanibek.newnewfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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

    FirebaseDatabase userDatabase;
    ChildEventListener messageChildEventListener;
    ChildEventListener usersChildEventListener;
    private ImageButton deleteBtn;
    ArrayList<Message> arrayList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.signout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, SignUp.class));
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Intent intent = getIntent();
        if(intent != null){
            userName = intent.getStringExtra("username");
        }

        Toolbar toolbar = new Toolbar(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 168);
        toolbar.setLayoutParams(layoutParams);
        toolbar.setPopupTheme(R.style.Base_Theme_NewnewFirebase);
        toolbar.setBackgroundColor(getResources().getColor(R.color.custom));
        toolbar.setTitle("This is the title");
        toolbar.setVisibility(View.VISIBLE);

        // Assuming in activity_main, you are using LinearLayout as root
        LinearLayout ll = (LinearLayout) findViewById(R.id.linear);
        ll.addView(toolbar, 0);

        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();




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

        userName = "Default name";

        arrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, R.layout.message_item, arrayList);
        listView.setAdapter(messageAdapter);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        

    }
    public void login(){
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }

}