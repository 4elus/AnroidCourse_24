package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private EditText etLoginId;
    private EditText etPassword;
    private Button btnLogin;
    UserDao userDao;
    List<User> testlist;
    TextView tvSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
//                        User testUser = new User();
//                        testUser.setLoginId("user");
//                        testUser.setPassword("user");
//                        testUser.setFullName("user User");
//                        testUser.setEmail("user@example.com");
//                        userDao.insert(testUser);

                        testlist = userDao.getAllUsers();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvSignUp.setText(testlist.get(0).getFullName());
                                Toast.makeText(LoginActivity.this, "created", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
    public void init(){
        userDao = Room.databaseBuilder(this, AppDatabase.class, DbConfig.ROOM_DB_NAME).build().userDao();
        //.addMigrations(AppDatabase.MIGRATION_1_2)
        etLoginId = findViewById(R.id.logInEditText);
        etPassword = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.logInButton);
        tvSignUp = findViewById(R.id.signUpText);
    }
}