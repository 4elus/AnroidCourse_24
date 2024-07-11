package com.alexander.roomdbproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private EditText etLoginId;
    private EditText etPassword;
    private EditText etName;
    private EditText etEmail;
    private Button btnSignUp;
    TextView signUp;

    UserDao userDao;
    ItemDao itemDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDao = Room.databaseBuilder(this,
                        AppDatabase.class, DbConfig.ROOM_DB_NAME)
                //.addMigrations(AppDatabase.MIGRATION_1_2)  // Add your migration(s)
                //.fallbackToDestructiveMigration()
                .build().userDao();




        // Initialize UI components
        etLoginId = findViewById(R.id.loginETS);
        etPassword = findViewById(R.id.passwordETS);
        etName = findViewById(R.id.nameETS);
        etEmail = findViewById(R.id.emailET);
        btnSignUp= findViewById(R.id.signUpBtn);
        signUp = findViewById(R.id.logInTV);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        User user = new User();
                        user.setLoginId(etLoginId.getText().toString());
                        user.setPassword(etPassword.getText().toString());  // Note: In a real application, passwords should be hashed
                        user.setFullName(etName.getText().toString());
                        user.setEmail(etEmail.getText().toString());
                        userDao.insert(user);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.putExtra("Id", user.getEmail());
                                startActivity(intent);
                            }
                        });
                    }
                });



            }
        });
    }
}