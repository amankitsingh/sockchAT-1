package com.example.dhp.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String customLog = "thisIsforCutomLog";
    public static String myServerIP;
    public static int myServerPort = 8080;
    public static String otherServerIP;
    public static int otherServerPort = 8080;
    EditText ipAddressEditText, portEditText;
    TextView runningAddress;
    Button connectButton;
    Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipAddressEditText = findViewById(R.id.addressEditText);
        portEditText = findViewById(R.id.portEditText);
        runningAddress = findViewById(R.id.runningAddress);
        connectButton = findViewById(R.id.connectButton);
        server = new Server(MainActivity.this);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipAddress = ipAddressEditText.getText().toString();
                String port = portEditText.getText().toString();
                int otherPort = 8080;
                if (port.length() != 0) {
                    otherPort = Integer.parseInt(port);
                }
                MainActivity.otherServerIP = ipAddress;
                MainActivity.otherServerPort = otherPort;

                String ipAndPort = myServerIP + "@" + myServerPort;
                Log.d(customLog, "ma sending post");
                new Client(ipAndPort);
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        server.onDestroy();
        startActivity(new Intent(MainActivity.this, ChatActivity.class));
    }
}
