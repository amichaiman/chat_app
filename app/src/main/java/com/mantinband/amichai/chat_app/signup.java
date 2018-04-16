package com.mantinband.amichai.chat_app;

import android.graphics.Color;
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

public class signup extends AppCompatActivity {
    EditText signUpEmailEditText;
    EditText signUpPasswordEditText;
    EditText confirmPasswordEditText;
    Button signUpButton;
    Animation shake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        signUpEmailEditText = findViewById(R.id.signUpEmailEditText);
        signUpPasswordEditText = findViewById(R.id.signUpPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signUpPasswordEditText.getText().toString().isEmpty() || signUpEmailEditText.getText().toString().isEmpty() || confirmPasswordEditText.getText().toString().isEmpty()){
                    if (signUpPasswordEditText.getText().toString().isEmpty()){
                        signUpPasswordEditText.startAnimation(shake);
                    }
                    if (signUpEmailEditText.getText().toString().isEmpty()){
                        signUpEmailEditText.startAnimation(shake);
                    }
                    if (confirmPasswordEditText.getText().toString().isEmpty()){
                        confirmPasswordEditText.startAnimation(shake);
                    }

                } else if (signUpPasswordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())){
                    FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(signUpEmailEditText.getText().toString(), signUpPasswordEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"sign up succesfull",Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {

                                    }
                                }
                            });
                } else {
                    confirmPasswordEditText.setTextColor(Color.RED);
                    confirmPasswordEditText.startAnimation(shake);
                }

            }

        });

    }
}
