package com.alisherzhanibek.newnewfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private MessageAdapter messageAdapter;
    private ListView listView;
    private ImageButton sendImageButton;
    private Button sendMessageButton;
    private EditText messageEditText;
    private String userName;
    FirebaseDatabase database;
    DatabaseReference myRef;

    FirebaseDatabase userDatabase;
    FirebaseAuth auth;
    ChildEventListener messageChildEventListener;
    ChildEventListener usersChildEventListener;
    private ImageButton deleteBtn;
    ArrayList<Message> arrayList;
    private static int RC_IMAGE_PICKER = 123;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference imageRef;
    StorageReference chatRef = storage.getReference();
    DatabaseReference userDatabaseReference;
    String recipientUserId;
    String recipientUserName;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.signout){
//            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ChatActivity.this, UserListActivity.class));
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
        Intent intent = getIntent();
        if(intent != null){
            userName = intent.getStringExtra("username");
            recipientUserId = intent.getStringExtra("recipientUserId");
            recipientUserName = intent.getStringExtra("recipientUserName");
        }else{
            userName = "Default user";
        }
        setTitle("Chat with " + recipientUserName);


        Toolbar toolbar = new Toolbar(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 168);
        toolbar.setLayoutParams(layoutParams);
        toolbar.setPopupTheme(R.style.Base_Theme_NewnewFirebase);
        toolbar.setBackgroundColor(getResources().getColor(R.color.custom));
//        toolbar.setTitle("This is the title");
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
                message.setSender(auth.getCurrentUser().getUid());
                message.setRecipient(recipientUserId);

                message.setImageurl(null);
                if(!messageEditText.getText().toString().isEmpty()){
                    myRef.push().setValue(message);

                }else{
                    sendMessageButton.isEnabled();
                    Toast.makeText(ChatActivity.this, "нельзя отправить пустое сообщение", Toast.LENGTH_SHORT).show();
                }
            }
        });

        messageChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                if (message.getSender().equals(auth.getCurrentUser().getUid()) && message.getRecipient().equals(recipientUserId)) {
                    messageAdapter.add(message);
                }else if (message.getRecipient().equals(auth.getCurrentUser().getUid()) && message.getSender().equals(recipientUserId)) {
                    messageAdapter.add(message);
                }
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

        usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                if(user.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    userName = user.getName();
                }
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
        userDatabaseReference.addChildEventListener(usersChildEventListener);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("message").removeValue();
                Intent intent = new Intent(ChatActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Choose an image"), RC_IMAGE_PICKER);
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
        chatRef = storage.getReference("ChatImages");
        userDatabaseReference = database.getReference("users");

        auth = FirebaseAuth.getInstance();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == RC_IMAGE_PICKER){
            Uri uri = data.getData();
            imageRef = chatRef.child(uri.getLastPathSegment());
            Toast.makeText(this, "Image: " + imageRef, Toast.LENGTH_SHORT).show();

            UploadTask uploadTask = imageRef.putFile(uri);
            uploadTask = imageRef.putFile(uri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Message message = new Message();
                        message.setSendIMG(downloadUri.toString());
                        message.setImageurl(downloadUri.toString());
                        message.setName(userName);
                        message.setRecipient(recipientUserId);
                        message.setSender(auth.getCurrentUser().getUid());
                        myRef.push().setValue(message);
                    } else {
                    }
                }
            });
        }
    }
}