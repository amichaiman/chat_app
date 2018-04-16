package com.mantinband.amichai.chat_app;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button signInButton;
    Button signUpButton;
    Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(getApplicationContext(), signup.class);
                startActivity(signUpIntent);
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
                    if (emailEditText.getText().toString().isEmpty()) {
                        emailEditText.startAnimation(shake);
                    }
                    if (passwordEditText.getText().toString().isEmpty()) {
                        passwordEditText.startAnimation(shake);
                    }

                } else {
                    FirebaseAuth.getInstance()
                            .signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent insideAppIntent = new Intent(getApplicationContext(), insideAppActivity.class);
                                        startActivity(insideAppIntent);
                                    } else {

                                        emailEditText.startAnimation(shake);
                                        passwordEditText.startAnimation(shake);
                                    }
                                }
                            });
                }
            }
        });
    }




}
