package com.mantinband.amichai.chat_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class insideAppActivity extends AppCompatActivity {
    TextView userNameTextView;
    TextView messageFromDataBaseTextView;
    EditText messageToSendToDataBaseEditText;
    Button sendMessageButton;
    Button signOut;
    Animation shake;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_app);

        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        signOut = findViewById(R.id.signOutButton);
        userNameTextView = findViewById(R.id.userNameTextView);
        messageFromDataBaseTextView = findViewById(R.id.messageFromDataBaseTextView);
        messageToSendToDataBaseEditText = findViewById(R.id.messageToSendToDataBaseEditText);
        sendMessageButton = findViewById(R.id.sendMessageButton);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("wassa");

        userNameTextView.setText("signed in as " + FirebaseAuth.getInstance().getCurrentUser().getEmail());

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (messageToSendToDataBaseEditText.getText().toString().isEmpty()){
                    messageToSendToDataBaseEditText.startAnimation(shake);
                } else {
                    reference.setValue(messageToSendToDataBaseEditText.getText().toString());
                }
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageFromDataBaseTextView.setText(dataSnapshot.getValue(String.class));
                messageFromDataBaseTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
