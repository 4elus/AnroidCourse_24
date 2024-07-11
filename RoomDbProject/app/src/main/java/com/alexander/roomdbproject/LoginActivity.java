package com.alexander.roomdbproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private EditText etLoginId;
    private EditText etPassword;
    private Button btnLogin;
    TextView signUp;

    UserDao userDao;
    ItemDao itemDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDao = Room.databaseBuilder(this,
                        AppDatabase.class, DbConfig.ROOM_DB_NAME)
                //.addMigrations(AppDatabase.MIGRATION_1_2)  // Add your migration(s)
                //.fallbackToDestructiveMigration()
                .build().userDao();

        itemDao = Room.databaseBuilder(this,
                        AppDatabase.class, DbConfig.ROOM_DB_NAME)
                //.addMigrations(AppDatabase.MIGRATION_1_2)  // Add your migration(s)
                //.fallbackToDestructiveMigration()
                .build().itemDao();


        // Initialize UI components
        etLoginId = findViewById(R.id.loginET);
        etPassword = findViewById(R.id.passwordET);
        btnLogin = findViewById(R.id.loginBtn);
        signUp = findViewById(R.id.signUpTV);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
//                        User testUser = new User();
//                        testUser.setLoginId(etLoginId.getText().toString());
//                        testUser.setPassword(etPassword.getText().toString());  // Note: In a real application, passwords should be hashed
//                        testUser.setFullName("user User");
//                        testUser.setEmail("user@example.com");
//                        userDao.insert(testUser);

                        List<User> testList = userDao.getAllUsers();
//                        Item item = new Item();
//                        item.setTitle("Pizza");
//                        item.setDescription("The best pizza");
//                        item.setPrice(1000);
//                        item.setImage(R.drawable.pizza);
//                        itemDao.insert(item);

                      //  List<Item> itemList = itemDao.getAllItems();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                signUp.setText(testList.get(0).getEmail() + " " + testList.get(0).getId());
                                Toast.makeText(LoginActivity.this, "Created item", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });


            }
        });

    }

    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}