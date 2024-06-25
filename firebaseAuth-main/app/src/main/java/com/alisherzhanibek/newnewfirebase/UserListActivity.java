package com.alisherzhanibek.newnewfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    UserAdapter adapter;
    ArrayList<User> arrayList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseDatabase userDatabase;
    ChildEventListener messageChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

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
    }

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
            startActivity(new Intent(UserListActivity.this, SignUp.class));
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

}