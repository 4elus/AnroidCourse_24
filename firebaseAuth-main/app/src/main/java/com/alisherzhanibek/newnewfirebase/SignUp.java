package com.alisherzhanibek.newnewfirebase;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    Button signUPBTN;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private static final String TAG = "SignInActivity";
    private FirebaseAuth auth;
    boolean logINSWITCH ;
    EditText confirmPassword;
    TextView logINTEXT;
    FirebaseDatabase database;
    DatabaseReference userDatabaseReference;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
        if(auth.getCurrentUser() != null){
            Intent intent = new Intent(SignUp.this, UserListActivity.class);
            startActivity(intent);
        }
        signUPBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });
    }
    public void init(){
        signUPBTN = findViewById(R.id.singUpBTN);
        nameEditText = findViewById(R.id.userName);
        emailEditText = findViewById(R.id.userEmail);
        passwordEditText = findViewById(R.id.userPassword);
        confirmPassword = findViewById(R.id.userConfirm);
        logINTEXT = findViewById(R.id.tapTOLOG);
        auth = FirebaseAuth.getInstance();


        database = FirebaseDatabase.getInstance();
        userDatabaseReference = database.getReference().child("users");
    }
    public void signUser(String email, String password){
        if(logINSWITCH){
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            if(passwordEditText.getText().toString().equals(confirmPassword.getText().toString() )){
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = auth.getCurrentUser();
                                        createUser(user);
                                        Intent intent = new Intent(SignUp.this, UserListActivity.class);
                                        intent.putExtra("username", nameEditText.getText().toString().trim());
                                        startActivity(intent);
                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
            }else{
                Intent intent = new Intent(SignUp.this, SignUp.class);
                startActivity(intent);
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void createUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail
                ());
        user.setName(nameEditText.getText()
                .toString().trim());
        userDatabaseReference.push().setValue(user);
    }
    public void logIN(View view) {
        if(logINSWITCH){
            logINSWITCH = false;
            logINTEXT.setText("Or, log in");

            signUPBTN.setText("Sign Up");
            confirmPassword.setVisibility(View.VISIBLE);
            nameEditText.setVisibility(View.VISIBLE);
        }else{
            logINSWITCH = true;
            logINTEXT.setText("Or, sign up");
            signUPBTN.setText("Log in");

            confirmPassword.setVisibility(View.GONE);
            nameEditText.setVisibility(View.GONE);
        }
    }
}