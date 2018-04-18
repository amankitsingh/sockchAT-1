package com.example.dhp.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    static List<Message> messageList;
    TextView messagehistoy;
    TextView songsHistory;
    EditText messageToSend;
    Button sendButton;
    Server server;
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        messageList = new ArrayList<>();
        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
        messagehistoy = findViewById(R.id.messageHistory);
        messageToSend = findViewById(R.id.messageToSend);
        sendButton = findViewById(R.id.sendButton);
        songsHistory = findViewById(R.id.songsHistory);
        server = new Server(this);

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
                new Client(message, ChatActivity.this);
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
