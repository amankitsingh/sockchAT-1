package com.example.dhp.chatapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String logTag = "customLogTag";

    TextView infoip, logText, response;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;
    Server server;

    public static String otherServerIP;
    public static int otherServerPort;
    public static String myServerIP;
    public static int myServerPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting up
        infoip = findViewById(R.id.infoip);
        logText = findViewById(R.id.logText);
        editTextAddress = findViewById(R.id.addressEditText);
        editTextPort = findViewById(R.id.portEditText);
        buttonConnect = findViewById(R.id.connectButton);
        buttonClear = findViewById(R.id.clearButton);
        response = findViewById(R.id.responseTextView);


        //start server
        server = new Server(this);
        infoip.setText(server.getIpAddress() + ":" + server.getPort());

        buttonConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Client myClient = new Client(MainActivity.this, editTextAddress.getText()
                        .toString(), Integer.parseInt(editTextPort
                        .getText().toString()), response);
                myClient.execute();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                response.setText("");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        server.onDestroy();
    }
}
