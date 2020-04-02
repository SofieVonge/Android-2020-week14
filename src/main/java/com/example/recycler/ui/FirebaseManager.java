package com.example.recycler.ui;

import androidx.annotation.NonNull;

import com.example.recycler.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.internal.IdTokenListener;
import com.google.firebase.internal.InternalTokenResult;

public class FirebaseManager {

    FirebaseAuth auth;
    private LoginActivity activity;

    public FirebaseManager(LoginActivity activity) {
        this.activity = activity;
        auth = FirebaseAuth.getInstance();
        setUpAuthStateListener();
    }

    private void setUpAuthStateListener() {
        auth.addIdTokenListener(new IdTokenListener() {
            @Override
            public void onIdTokenChanged(@NonNull InternalTokenResult internalTokenResult) {
                if (auth.getCurrentUser() == null) {
                    System.out.println("Signed out");
                } else  {
                    System.out.println("Signed in");
                }
            }
        });
    }

    //sign up
    public void signUp(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println(task.getResult().getUser().getEmail() + " is now signed up");
                        } else {
                            System.out.println("Could not sign up " + task.getResult().getUser().getEmail());
                        }
                    }
                });
    }

    //sign in with email and password
    public void signIn(String email, String password, final LoginActivity activity) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            activity.startMainActivity();
                            System.out.println("Success " + task.getResult().getUser().getEmail());

                        } else {
                            System.out.println("Failed " + task.getException());
                        }
                    }
                });
    }

    public void signOut() {
        auth.signOut();
    }
}

