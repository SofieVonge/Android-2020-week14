package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.recycler.ui.FirebaseManager;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    private FirebaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager = new FirebaseManager(this);

        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
    }

    public void signIn(View view) {
        String mail = email.getText().toString();
        String ps = password.getText().toString();
        if (mail.length() > 0 && ps.length() > 0) {
            manager.signIn(mail, ps, this);
        }
    }

    public void signUp(View view) {
        String mail = email.getText().toString();
        String ps = password.getText().toString();
        if (mail.length() > 0 && ps.length() > 0) {
            manager.signUp(mail, ps);
        }
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.signOut();
    }
}
