package com.example.dhp.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {
    TextView messagehistoy;
    EditText messageToSend;
    Button sendButton;
    Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messagehistoy = findViewById(R.id.messageHistory);
        messageToSend = findViewById(R.id.messageToSend);
        sendButton = findViewById(R.id.sendButton);
        server=new Server(this);

        Toast.makeText(getApplicationContext(),
                "myServerIP:" + MainActivity.myServerIP +
                        "\notherServer:" + MainActivity.otherServerIP +
                        "\nmyServerPort:" + MainActivity.myServerPort +
                        "\notherServerPort" + MainActivity.otherServerPort,
                Toast.LENGTH_LONG).show();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageToSend.getText().toString();
                messageToSend.setText("");
                new Client(message);
                messagehistoy.append("\nYou:" + message);
            }
        });
    }


    @Override
        protected void onDestroy() {
            super.onDestroy();
            server.onDestroy();
        }
}
