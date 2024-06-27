package com.alisherzhanibek.newnewfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    private UserAdapter adapter;
    ArrayList<User> arrayList;
    private String userName;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ChildEventListener usersChildEventListener;
    RecyclerView recyclerView;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        init();

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

        usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                if (!user.getId().equals(auth.getCurrentUser().getUid()) ){
                    arrayList.add(user);
                    adapter.notifyDataSetChanged();
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
        myRef.addChildEventListener(usersChildEventListener);

        adapter.setOnUserClickListener(new UserAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(int position) {
                Intent intent = new Intent(UserListActivity.this, ChatActivity.class);
                intent.putExtra("recipientUserId", arrayList.get(position).getId());
                intent.putExtra("recipientUserName", arrayList.get(position).getName());
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_activity, menu);
//        return true;
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.signout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(UserListActivity.this, SignUp.class));
            return true;

        }else{
            return super.onOptionsItemSelected(item);
        }
    }
    public void init(){
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        recyclerView = findViewById(R.id.userList);

        recyclerView.setHasFixedSize(true);
        adapter = new UserAdapter((ArrayList<User>) arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        auth = FirebaseAuth.getInstance();
    }

}