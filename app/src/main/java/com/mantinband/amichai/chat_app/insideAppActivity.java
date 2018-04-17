package com.mantinband.amichai.chat_app;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class insideAppActivity extends AppCompatActivity {
    TextView userNameTextView;
    EditText messageToSendToDataBaseEditText;
    Button sendMessageButton;
    Button signOut;
    Animation shake;
    FirebaseDatabase database;
    DatabaseReference reference;
    LinearLayout messageLinearLayout;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_app);

        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        signOut = findViewById(R.id.signOutButton);
        userNameTextView = findViewById(R.id.userNameTextView);
        messageToSendToDataBaseEditText = findViewById(R.id.messageToSendToDataBaseEditText);
        sendMessageButton = findViewById(R.id.sendMessageButton);
        messageLinearLayout = findViewById(R.id.messageLinearLayout);
        scrollView = findViewById(R.id.scrollView);
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
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                    params.setMargins(0,5,0,5);
                    params.gravity = Gravity.RIGHT;
                    TextView textView = new EditText(getApplicationContext());
                    textView.setBackgroundResource(R.drawable.sent_message);
                    textView.setText(messageToSendToDataBaseEditText.getText().toString());
                    textView.setTextColor(Color.BLACK);
                    textView.setLayoutParams(params);
                    textView.setPadding(15,15,15,15);
                    textView.setEnabled(false);
                    messageLinearLayout.addView(textView,messageLinearLayout.getChildCount());
                    messageToSendToDataBaseEditText.setText("");
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                }
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                params.setMargins(0,5,0,5);
                TextView textView = new EditText(getApplicationContext());
                textView.setBackgroundResource(R.drawable.recieved_message);
                textView.setText(dataSnapshot.getValue(String.class));
                textView.setTextColor(Color.BLACK);
                textView.setLayoutParams(params);
                textView.setEnabled(false);
                textView.setPadding(15,15,15,15);
                messageLinearLayout.addView(textView,messageLinearLayout.getChildCount());
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
