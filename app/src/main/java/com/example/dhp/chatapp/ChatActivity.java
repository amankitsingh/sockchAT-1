package com.example.dhp.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {
    EditText typeMessage;
    TextView messageHistory;
    Button sendButton;
    ComplexServer complexServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        typeMessage = findViewById(R.id.typeMessage);
        messageHistory = findViewById(R.id.messageHistory);
        sendButton = findViewById(R.id.sendButton);
        complexServer = new ComplexServer(this);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = typeMessage.getText().toString();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new SimpleClient(message);
                    }
                });
                thread.start();

                messageHistory.append("\nYou:" + message);
                typeMessage.setText("");
            }
        });

    }
}
